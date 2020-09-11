package io.unthrottled.doki.stickers.impl

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.ApplicationManager.getApplication
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.application.ex.ApplicationInfoEx
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.text.StringUtil.toHexString
import com.intellij.openapi.wm.impl.IdeBackgroundUtil
import io.unthrottled.doki.assets.AssetCategory
import io.unthrottled.doki.assets.AssetManager
import io.unthrottled.doki.assets.LocalStorageService.createDirectories
import io.unthrottled.doki.assets.LocalStorageService.getLocalAssetDirectory
import io.unthrottled.doki.stickers.StickerService
import io.unthrottled.doki.themes.DokiTheme
import io.unthrottled.doki.util.readAllTheBytes
import io.unthrottled.doki.util.toOptional
import org.apache.commons.io.IOUtils
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths.get
import java.nio.file.StandardOpenOption
import java.security.MessageDigest
import java.util.*
import java.util.Optional.empty
import java.util.Optional.ofNullable
import java.util.concurrent.TimeUnit

const val DOKI_BACKGROUND_PROP: String = "io.unthrottled.doki.background"

const val DOKI_STICKER_PROP: String = "io.unthrottled.doki.stickers"
private const val PREVIOUS_STICKER = "io.unthrottled.doki.sticker.previous"

class StickerServiceImpl : StickerService {

  override fun activateForTheme(dokiTheme: DokiTheme) {
    listOf({
      installSticker(dokiTheme)
    },
      {
        installBackgroundImage(dokiTheme)
      }
    ).forEach {
      getApplication().executeOnPooledThread(it)
    }
  }

  override fun checkForUpdates(dokiTheme: DokiTheme) {
    getApplication().executeOnPooledThread {
      getLocallyInstalledBackgroundImagePath(dokiTheme)
      getLocallyInstalledStickerPath(dokiTheme)
    }
  }

  private fun installSticker(dokiTheme: DokiTheme) =
    getLocallyInstalledStickerPath(dokiTheme)
      .ifPresent {
        setBackgroundImageProperty(
          it,
          "98",
          IdeBackgroundUtil.Fill.PLAIN.name,
          IdeBackgroundUtil.Anchor.BOTTOM_RIGHT.name,
          DOKI_STICKER_PROP
        )
      }

  private fun installBackgroundImage(dokiTheme: DokiTheme) =
    getLocallyInstalledBackgroundImagePath(dokiTheme)
      .ifPresent {
        setBackgroundImageProperty(
          it,
          "100",
          IdeBackgroundUtil.Fill.SCALE.name,
          IdeBackgroundUtil.Anchor.CENTER.name,
          DOKI_BACKGROUND_PROP
        )
      }

  private fun getLocallyInstalledBackgroundImagePath(
    dokiTheme: DokiTheme
  ): Optional<String> =
    dokiTheme.getSticker()
      .flatMap {
        AssetManager.resolveAssetUrl(
          AssetCategory.BACKGROUNDS,
          it
        )
      }

  private fun getLocallyInstalledStickerPath(
    dokiTheme: DokiTheme
  ): Optional<String> =
    dokiTheme.getStickerPath()
      .flatMap {
        AssetManager.resolveAssetUrl(
          AssetCategory.STICKERS,
          it
        )
      }

  override fun remove() {
    val propertiesComponent = PropertiesComponent.getInstance()
    val previousSticker = propertiesComponent.getValue(DOKI_STICKER_PROP, "")
    if (previousSticker.isNotEmpty()) {
      propertiesComponent.setValue(
        PREVIOUS_STICKER,
        previousSticker
      )
    }

    propertiesComponent.unsetValue(DOKI_STICKER_PROP)
    propertiesComponent.unsetValue(DOKI_BACKGROUND_PROP)
    repaintWindows()
  }

  private fun repaintWindows() = try {
    IdeBackgroundUtil.repaintAllWindows()
  } catch (e: Throwable) {
  }

  override fun getPreviousSticker(): Optional<String> =
    PropertiesComponent.getInstance().getValue(PREVIOUS_STICKER).toOptional()

  override fun clearPreviousSticker() {
    PropertiesComponent.getInstance().unsetValue(PREVIOUS_STICKER)
  }

  private fun setBackgroundImageProperty(
    imagePath: String,
    opacity: String,
    fill: String,
    anchor: String,
    propertyKey: String
  ) {
    // org.intellij.images.editor.actions.SetBackgroundImageDialog has all of the answers
    // as to why this looks this way
    val propertyValue = listOf(imagePath, opacity, fill, anchor)
      .reduceRight { a, b -> "$a,$b" }
    setPropertyValue(propertyKey, propertyValue)
  }

  private fun setPropertyValue(propertyKey: String, propertyValue: String) {
    PropertiesComponent.getInstance().unsetValue(propertyKey)
    PropertiesComponent.getInstance().setValue(propertyKey, propertyValue)
  }
}
