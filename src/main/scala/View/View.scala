package View

object View {

  //  Kriegt stand jetzt noch keine Daten aber gibt zurück wie das Spielfeld aussehen soll.
  def produceCLIView(): String = {

    val outString: String = """
        |           Welcome to SE-Poker!
        |
        |*********************************************
        |*    [PA][KK]   [*][*]   [*][*]   [*][*]    *
        |*                                           *
        |*               [K6][PA][*][*][*]           *
        |*                                           *
        |*    [*][*]   [*][*]   [*][*]   [*][*]      *
        |*********************************************
        |""".stripMargin

    outString;
  }
}
