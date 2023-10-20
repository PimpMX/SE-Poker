package de.htwg.model

import de.htwg.model.GameField

object GameHandler {

  private var game: Option[GameField] = None

  def initGame(numPlayers: Int): GameField = {

    if(game.isEmpty) {

      /*

      val Player1 = new Player(0,
        (new Card(Color.PIP, Rank.ACE), new Card(Color.SPADES, Rank.TWO)), 1000, 0)

      val Player2 = new Player(1,
        (new Card(Color.PIP, Rank.ACE), new Card(Color.SPADES, Rank.TWO)), 1000, 0)

      val Player3 = new Player(2,
        (new Card(Color.PIP, Rank.ACE), new Card(Color.SPADES, Rank.TWO)), 1000, 0)

      val Player4 = new Player(3,
        (new Card(Color.PIP, Rank.ACE), new Card(Color.SPADES, Rank.TWO)), 1000, 0)
       */

      val players: Vector[Player] = Vector(new Player(0,
        new Hand((new Card(Color.PIP, Rank.ACE), new Card(Color.CLUBS, Rank.ACE))),
        1000, 0))

      game = Some(new GameField(numPlayers, players))
    }

    game.get
  }

  def getCardSet(): Vector[Card] = {
    Vector(
      new Card(Color.PIP, Rank.ACE),
      new Card(Color.PIP, Rank.TWO),
      new Card(Color.PIP, Rank.THREE),
      new Card(Color.PIP, Rank.FOUR),
      new Card(Color.PIP, Rank.FIVE),
      new Card(Color.PIP, Rank.SIX),
      new Card(Color.PIP, Rank.SEVEN),
      new Card(Color.PIP, Rank.EIGHT),
      new Card(Color.PIP, Rank.NINE),
      new Card(Color.PIP, Rank.TEN),
      new Card(Color.PIP, Rank.JACK),
      new Card(Color.PIP, Rank.QUEEN),
      new Card(Color.PIP, Rank.KING),
      new Card(Color.HEARTS, Rank.ACE),
      new Card(Color.HEARTS, Rank.TWO),
      new Card(Color.HEARTS, Rank.THREE),
      new Card(Color.HEARTS, Rank.FOUR),
      new Card(Color.HEARTS, Rank.FIVE),
      new Card(Color.HEARTS, Rank.SIX),
      new Card(Color.HEARTS, Rank.SEVEN),
      new Card(Color.HEARTS, Rank.EIGHT),
      new Card(Color.HEARTS, Rank.NINE),
      new Card(Color.HEARTS, Rank.TEN),
      new Card(Color.HEARTS, Rank.JACK),
      new Card(Color.HEARTS, Rank.QUEEN),
      new Card(Color.HEARTS, Rank.KING),
      new Card(Color.DIAMONDS, Rank.ACE),
      new Card(Color.DIAMONDS, Rank.TWO),
      new Card(Color.DIAMONDS, Rank.THREE),
      new Card(Color.DIAMONDS, Rank.FOUR),
      new Card(Color.DIAMONDS, Rank.FIVE),
      new Card(Color.DIAMONDS, Rank.SIX),
      new Card(Color.DIAMONDS, Rank.SEVEN),
      new Card(Color.DIAMONDS, Rank.EIGHT),
      new Card(Color.DIAMONDS, Rank.NINE),
      new Card(Color.DIAMONDS, Rank.TEN),
      new Card(Color.DIAMONDS, Rank.JACK),
      new Card(Color.DIAMONDS, Rank.QUEEN),
      new Card(Color.DIAMONDS, Rank.KING),
      new Card(Color.SPADES, Rank.ACE),
      new Card(Color.SPADES, Rank.TWO),
      new Card(Color.SPADES, Rank.THREE),
      new Card(Color.SPADES, Rank.FOUR),
      new Card(Color.SPADES, Rank.FIVE),
      new Card(Color.SPADES, Rank.SIX),
      new Card(Color.SPADES, Rank.SEVEN),
      new Card(Color.SPADES, Rank.EIGHT),
      new Card(Color.SPADES, Rank.NINE),
      new Card(Color.SPADES, Rank.TEN),
      new Card(Color.SPADES, Rank.JACK),
      new Card(Color.SPADES, Rank.QUEEN),
      new Card(Color.SPADES, Rank.KING)
    )
  }

}
