package io.acari.DDLC.themes.anthro

import com.chrisrm.ideaddlc.utils.MTAccents
import com.chrisrm.ideaddlc.utils.PropertiesParser
import io.acari.DDLC.themes.AnthroTheme
import io.acari.DDLC.toStream
import java.awt.Color
import java.util.stream.Stream
import javax.swing.plaf.ColorUIResource

class CleoTheme : AnthroTheme("cleo", "Cleo", false, "Cleo") {

  override fun getChibi(): String = "anthro/cleo.png"

  override fun getNormalChibi(): String = "anthro/cleo.png"

  override fun getBackgroundColorString(): String = "e6e6f1"

  //todo: remove these
  override fun getClubMember(): String = "sayori.png"

  override fun joyfulClubMember(): String = "sayori_joy.png"

  override fun getSecondaryBackgroundColorString(): String = "dddce9"

  override fun getSecondaryForegroundColorString(): String = "36363a"

  override fun getSelectionForegroundColorString(): String = "e7e7ed"

  override fun getSelectionBackgroundColorString(): String = "55545d"

  override fun getTreeSelectionBackgroundColorString(): String = "242528"

  override fun getTreeSelectionForegroundColorString(): String = "e7e7ed"

  override fun getForegroundColorString(): String = "1F2435"

  override fun getTextColorString(): String = "1F2435"

  override fun getInactiveColorString(): String = "C4C3CF"

  override fun getTreeSelectionInactiveColor(): Color? = PropertiesParser.parseColor(getInactiveColorString())

  override fun getMenuItemForegroundColor(): String = "252529"

  override fun getMenuBarSelectionForegroundColorString(): String = "242528"

  override fun getMenuBarSelectionBackgroundColorString(): String = "C4C3CF"

  override fun getNotificationsColorString(): String = "DCDDE8"

  override fun getHighlightColorString(): String = "DCE0F0"

  override fun getContrastColorString(): String = "CFCFD5"

  override fun getBorderColorString(): String = "d0cdd5"

  override fun getEditorTabColorString(): String = contrastColorString

  override fun getButtonBackgroundColor(): String = contrastColorString

  override fun getButtonForegroundColor(): String = "252427"

  override fun getAccentColor(): String = MTAccents.COLBALT.hexColor

  override fun getBackgroundColorResource(): ColorUIResource = ColorUIResource(0xe7e7ed)

  override fun getForegroundColorResource(): ColorUIResource = ColorUIResource(0x0F111A)

  override fun getDisabledColorString(): String = "A2B6CB"

  override fun getTableSelectedColorString(): String = "242528"

  override fun getStartColor(): String = "252d33"

  override fun getStopColor(): String = "6accfc"

  override fun getNonProjectFileScopeColor(): String = "eceae8"

  override fun getTestScope(): String = "C0EDCC"

  override fun getSelectedButtonForegroundColor(): String = selectionForegroundColorString

  override fun getCompletionPopupBackgroundColor(): String = "eaeaf0"

  override fun getMenuBarColorString(): String = "e3dfe8"

  private val naughtyForegroundSet: Set<String> = setOf(
      "Label.selectedForeground",
      "SearchEverywhere.Tab.selectedForeground"
  )

  private val naughtyBackgroundSet: Set<String> = setOf(
      "Button.select",
      "ToolWindow.Button.hoverBackground"
  )

  private val badHighlightProperties: Set<String> = setOf(
      "DefaultTabs.hoverColor",
      "DefaultTabs.hoverMaskColor",
      "EditorTabs.hoverColor",
      "EditorTabs.hoverMaskColor",
      "Plugins.Tab.hover.background",
      "TabbedPane.hoverColor"
  )

  override fun getInactiveResources(): Stream<String> {
    return Stream.concat(super.getInactiveResources(),
        badHighlightProperties.stream())
  }

  override fun getHighlightResources(): Stream<String> {
    return super.getHighlightResources()
        .filter { !badHighlightProperties.contains(it) }
  }

  override fun getSelectionForegroundResources(): Stream<String> {
    return super.getSelectionForegroundResources()
        .filter { !naughtyForegroundSet.contains(it) }
  }

  override fun getSelectionBackgroundResources(): Stream<String> {
    return super.getSelectionBackgroundResources()
        .filter { !naughtyBackgroundSet.contains(it) }
  }

  override fun getTableSelectedResources(): Stream<String> {
    return super.getTableSelectedResources()
        .filter { !naughtyBackgroundSet.contains(it) }
  }

  override fun getBackgroundResources(): Stream<String> {
    return Stream.concat(super.getBackgroundResources(),
        naughtyBackgroundSet.stream())
  }

  override fun getForegroundResources(): Stream<String> {
    return Stream.concat(super.getForegroundResources(),
        naughtyForegroundSet.stream())
  }

  override fun getOneOffResources(): Stream<Pair<Stream<String>, String>> {
    return Stream.of(
        Pair(Stream.of("Menu.foreground",
            "MenuItem.foreground",
            "Menu.acceleratorForeground",
            "MenuItem.acceleratorForeground"), "766B6B"),
        Pair("CompletionPopup.selectionForeground".toStream(), selectionForegroundColorString),
        Pair(Stream.of(
            "CompletionPopup.matchForeground",
            "CompletionPopup.matchSelectedForeground",
            "CompletionPopup.matchSelectionForeground"
        ), "3961F6")
    )
  }
}