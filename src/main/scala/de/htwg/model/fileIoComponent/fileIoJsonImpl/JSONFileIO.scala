package de.htwg.model.fileIoComponent.fileIoJsonImpl

import de.htwg.model.fileIoComponent.FileIOInterface
import de.htwg.model.gameFieldComponent.Hand
import de.htwg.model.gameFieldComponent._
import play.api.libs.json._
import java.io._
import de.htwg.TexasHoldEm
import de.htwg.TexasHoldEmModule
import com.google.inject.Guice
import de.htwg.model.gameFieldComponent.comCardBaseImpl.CommunityCardFactory
import scala.io.BufferedSource

class JSONFileIO extends FileIOInterface {

  val injector = Guice.createInjector(new TexasHoldEmModule)
  val cardFactory = injector.getInstance(classOf[CardFactoryInterface])
  val playerFactory = injector.getInstance(classOf[PlayerFactoryInterface])
  val comCardsFactory = injector.getInstance(classOf[CommunityCardsFactoryInterface])
  val comCardFactory = injector.getInstance(classOf[CommunityCardFactoryInterface])
  val gameFieldFactory = injector.getInstance(classOf[GameFieldFactoryInterface])

  override def load: GameFieldInterface = {
    val source: BufferedSource = scala.io.Source.fromFile("gameField.json")
    val jsonString = source.getLines.mkString
    source.close
    val json: JsValue = Json.parse(jsonString)
    val gameField = json.as[GameFieldInterface]
    gameField
  }
  
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
      JsSuccess(new Hand(cards(0), cards(1)))
    }
  }

  implicit val cardWrites: Writes[CardInterface] = new Writes[CardInterface] {
    def writes(card: CardInterface) = Json.obj(
      "suit" -> card.getColor.toString(),
      "rank" -> card.getRank.toString(),
    )
  }

  implicit val cardReads: Reads[CardInterface] = new Reads[CardInterface] {
    def reads(json: JsValue) = {

      val suit = (json \ "suit").as[String] match {
        case "SPADES" => SPADES
        case "HEARTS" => HEARTS
        case "DIAMONDS" => DIAMONDS
        case "CLUBS" => CLUBS
        case _ => throw new IllegalArgumentException("Invalid suit value" + (json \ "suit").toString())
      }

      val rank = (json \ "rank").as[String] match {
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

      JsSuccess(cardFactory(suit, rank))
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

  implicit val playerReads: Reads[PlayerInterface] = new Reads[PlayerInterface] {
    def reads(json: JsValue) = {
      val playerNum = (json \ "playerNum").as[Int]
      val balance = (json \ "balance").as[Int]
      val moneyInPool = (json \ "moneyInPool").as[Int]
      val hand = (json \ "hand").as[Hand]
      val foldedStatus = (json \ "foldedStatus").as[Boolean]

      JsSuccess(playerFactory(playerNum,
        hand,
        balance,
        moneyInPool,
        foldedStatus))
    }
  }

  implicit val comCardsWrites: Writes[CommunityCardsInterface] = new Writes[CommunityCardsInterface] {
    def writes(comCards: CommunityCardsInterface) = Json.obj(
      "cards" -> Json.toJson(comCards.getCards)(Writes.seq(comCardWrites))
    )
  }

  implicit val comCardsReads: Reads[CommunityCardsInterface] = new Reads[CommunityCardsInterface] {
    def reads(json: JsValue) = {
      val cards = (json \ "cards").as[Vector[CommunityCardInterface]]
      JsSuccess(comCardsFactory(cards))
    }
  }

  implicit val comCardWrites: Writes[CommunityCardInterface] = new Writes[CommunityCardInterface] {
    def writes(card: CommunityCardInterface) = Json.obj(
      "suit" -> card.getColor.toString(),
      "rank" -> card.getRank.toString(),
      "isRevealed" -> card.isRevealed
    )
  }

  implicit val comCardReads: Reads[CommunityCardInterface] = new Reads[CommunityCardInterface] {
    def reads(json: JsValue) = {

      val suit = (json \ "suit").as[String] match {
        case "SPADES" => SPADES
        case "HEARTS" => HEARTS
        case "DIAMONDS" => DIAMONDS
        case "CLUBS" => CLUBS
        case _ => throw new IllegalArgumentException("Invalid suit value" + (json \ "suit").toString())
      }

      val rank = (json \ "rank").as[String] match {
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

      val isRevealed = (json \ "isRevealed").as[Boolean]

      JsSuccess(comCardFactory(suit, rank, isRevealed))
    }
  }

  implicit val gameFieldWrites: Writes[GameFieldInterface] = new Writes[GameFieldInterface] {
    def writes(gameField: GameFieldInterface) = Json.obj(
      "players" -> gameField.getPlayers,
      "comCards" -> gameField.getCommunityCards,
      "playerAtTurn" -> gameField.getPlayerAtTurn.getPlayerNum,
      "lastRaisePlayerIdx" -> gameField.getLastRaisePlayerIdx,
      "bigBlindPlayerIdx" -> gameField.getBigBlindPlayerIdx,
      "bettingRound" -> gameField.getBettingRound.toString(),
      "highestBet" -> gameField.getHighestBet
    )
  }

  implicit val gameFieldReads: Reads[GameFieldInterface] = new Reads[GameFieldInterface] {
    def reads(json: JsValue) = {
      val players = (json \ "players").as[Vector[PlayerInterface]]
      val comCards = (json \ "comCards").as[CommunityCardsInterface]
      val playerAtTurn = (json \ "playerAtTurn").as[Int]
      val lastRaisePlayerIdx = (json \ "lastRaisePlayerIdx").as[Int]
      val bigBlindPlayerIdx = (json \ "bigBlindPlayerIdx").as[Int]
      val bettingRound = (json \ "bettingRound").as[String] match {
        case "PREFLOP" => PREFLOP
        case "FLOP" => FLOP
        case "TURN" => TURN
        case "RIVER" => RIVER
      }
      val highestBet = (json \ "highestBet").as[Int]

      JsSuccess(gameFieldFactory(players, comCards, playerAtTurn, lastRaisePlayerIdx, bigBlindPlayerIdx, bettingRound, highestBet))
    }
  }
}
