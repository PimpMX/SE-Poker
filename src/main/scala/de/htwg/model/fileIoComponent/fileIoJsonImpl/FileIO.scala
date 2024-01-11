package de.htwg.model.fileIoComponent.fileIoJsonImpl

import de.htwg.model.fileIoComponent.FileIOInterface
import de.htwg.model.gameFieldComponent.Hand
import de.htwg.model.gameFieldComponent._
import play.api.libs.json._
import java.io._
import de.htwg.TexasHoldEm
import de.htwg.TexasHoldEmModule
import com.google.inject.Guice

class FileIO extends FileIOInterface {

  val injector = Guice.createInjector(new TexasHoldEmModule)
  val cardFactory = injector.getInstance(classOf[CardFactoryInterface])

  override def load: GameFieldInterface = ???
  override def save(gameField: GameFieldInterface): Unit = {
    val pw = new PrintWriter(new File("gameField.json"))
    pw.write(Json.prettyPrint(Json.toJson(gameField)))
    pw.close
  }

  implicit val handWrites: Writes[Hand] = new Writes[Hand] {
    def writes(hand: Hand) = Json.obj(
      "cards" -> hand.cards
    )
  }

  implicit val handReads: Reads[Hand] = new Reads[Hand] {
    def reads(json: JsValue) = {
      val cards = (json \ "cards").as[List[CardInterface]]
      new Hand(cards)
    }
  }

  implicit val cardWrites: Writes[CardInterface] = new Writes[CardInterface] {
    def writes(card: CardInterface) = Json.obj(
      "suit" -> card.getColor,
      "rank" -> card.getRank,
    )
  }

  implicit val cardReads: Reads[CardInterface] = new Reads[CardInterface] {
    def reads(json: JsValue) = {

      val suit = (json \ "suit").toString() match {
        case "Spades" => SPADES
        case "Hearts" => HEARTS
        case "Diamonds" => DIAMONDS
        case "Clubs" => CLUBS
      }

      val rank = (json \ "rank").toString() match {
        case "TWO" => TWO
        case "THREE" => THREE
        case "FOUR" => FOUR
        case "FIVE" => FIVE
        case "SIX" => SIX
        case "SEVEN" => SEVEN
        case "EIGHT" => EIGHT
        case "NINE" => NINE
        case "TEN" => TEN
        case "JACK" => JACK
        case "QUEEN" => QUEEN
        case "KING" => KING
        case "ACE" => ACE
      }

      cardFactory(suit, rank)
    }
  }

  implicit val playerWrites: Writes[PlayerInterface] = new Writes[PlayerInterface] {
    def writes(player: PlayerInterface) = Json.obj(
      "playerNum" -> player.getPlayerNum,
      "balance" -> player.getBalance,
      "moneyInPool" -> player.getMoneyInPool,
      "hand" -> player.getHand,
      "foldedStatus" -> player.getFoldedStatus
    )
  }

  implicit val comCardsWrites: Writes[CommunityCardsInterface] = new Writes[CommunityCardsInterface] {
    def writes(comCards: CommunityCardsInterface) = Json.obj(
      "cards" -> Json.toJson(comCards.getCards)(Writes.seq(comCardWrites))
    )
  }

  implicit val comCardWrites: Writes[CommunityCardInterface] = new Writes[CommunityCardInterface] {
    def writes(card: CommunityCardInterface) = Json.obj(
      "suit" -> card.getColor,
      "rank" -> card.getRank,
      "isRevealed" -> card.isRevealed
    )
  }

  implicit val colorWrites: Writes[Color] = new Writes[Color] {
    def writes(color: Color) = Json.obj(
      "color" -> color.toString
    )
  }

  implicit val rankWrites: Writes[Rank] = new Writes[Rank] {
    def writes(rank: Rank) = Json.obj(
      "rank" -> rank.toString
    )
  }

  implicit val gameFieldWrites: Writes[GameFieldInterface] = new Writes[GameFieldInterface] {
    def writes(gameField: GameFieldInterface) = Json.obj(
      "players" -> gameField.getPlayers,
      "comCards" -> gameField.getCommunityCards,
      "playerAtTurn" -> gameField.getPlayerAtTurn,
      "lastRaisePlayerIdx" -> gameField.getLastRaisePlayerIdx,
      "bigBlindPlayerIdx" -> gameField.getBigBlindPlayerIdx,
      "bettingRound" -> gameField.getBettingRound.toString(),
      "highestBet" -> gameField.getHighestBet
    )
  }
}
