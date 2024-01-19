package de.htwg.model.fileIoComponent.fileIoXmlImpl

import de.htwg.TexasHoldEmModule
import play.api.libs.json._
import java.io._
import com.google.inject.Guice
import scala.io.BufferedSource
import de.htwg.model.fileIoComponent.FileIOInterface
import de.htwg.model.gameFieldComponent._
import scala.xml._

class XMLFileIO extends FileIOInterface {

  val injector = Guice.createInjector(new TexasHoldEmModule)
  val cardFactory = injector.getInstance(classOf[CardFactoryInterface])
  val playerFactory = injector.getInstance(classOf[PlayerFactoryInterface])
  val comCardsFactory = injector.getInstance(classOf[CommunityCardsFactoryInterface])
  val comCardFactory = injector.getInstance(classOf[CommunityCardFactoryInterface])
  val gameFieldFactory = injector.getInstance(classOf[GameFieldFactoryInterface])

  override def load: GameFieldInterface = {
    val xml = XML.loadFile("gameField.xml")
    val gameField = readGameField(xml)
    gameField
  }

  override def save(gameField: GameFieldInterface): Unit = {
    val xml = writeGameField(gameField)
    XML.save("gameField.xml", xml)
  }

  def readHand(xml: Node): Hand = {
    val cards = readCards(xml \ "cards")
    new Hand(cards(0), cards(1))
  }

  def writeHand(hand: Hand): Elem = {
    <cards>
      {writeCard(hand.cards._1)}
      {writeCard(hand.cards._1)}
    </cards>
  }

  def readCards (xml: NodeSeq): Vector[CardInterface] = {
    (xml \ "card").map(card => readCard(card)).toVector
  }

  def readCard(xml: Node): CardInterface = {

    val suit = (xml \ "suit").text match {
      case "SPADES" => SPADES
      case "HEARTS" => HEARTS
      case "DIAMONDS" => DIAMONDS
      case "CLUBS" => CLUBS
    }

    val rank = (xml \ "rank").text match {
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

  def writeCard(card: CardInterface): Elem = {
    <card>
      <suit>{card.getColor.toString()}</suit>
      <rank>{card.getRank.toString()}</rank>
    </card>
  }

  def readPlayer(xml: Node): PlayerInterface = {

    val playerNum = (xml \ "playerNum").text.toInt
    val balance = (xml \ "balance").text.toInt
    val moneyInPool = (xml \ "moneyInPool").text.toInt
    val hand = readHand((xml \ "hand").head)
    val foldedStatus = (xml \ "foldedStatus").text.toBoolean

    playerFactory(playerNum, hand, balance, moneyInPool, foldedStatus)
  }

  def readPlayers(xml: NodeSeq): Vector[PlayerInterface] = {
    (xml \ "player").map(player => readPlayer(player)).toVector
  }

  def writePlayers(players: Vector[PlayerInterface]): Elem = {
    <players>
      {players.map(player => writePlayer(player))}
    </players>
  }

  def writePlayer(player: PlayerInterface): Elem = {
    <player>
      <playerNum>{player.getPlayerNum}</playerNum>
      <balance>{player.getBalance}</balance>
      <moneyInPool>{player.getMoneyInPool}</moneyInPool>
      <hand>{writeHand(player.getHand)}</hand>
      <foldedStatus>{player.getFoldedStatus}</foldedStatus>
    </player>
  }

  def readComCards(xml: Node): CommunityCardsInterface = {
    val cards = (xml \ "cards" \ "card").map(card => readCommunityCard(card))
    comCardsFactory(cards.toVector)
  }

  def writeComCards(comCards: CommunityCardsInterface): Elem = {
    <cards>
      {comCards.getCards.map(card => writeCommunityCard(card))}
    </cards>
  }

  def readCommunityCard(xml: Node): CommunityCardInterface = {

    val suit = (xml \ "suit").text match {
      case "SPADES" => SPADES
      case "HEARTS" => HEARTS
      case "DIAMONDS" => DIAMONDS
      case "CLUBS" => CLUBS
    }

    val rank = (xml \ "rank").text match {
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

    val isRevealed = (xml \ "isRevealed").text.toBoolean

    comCardFactory(suit, rank, isRevealed)
  }

  def writeCommunityCard(card: CommunityCardInterface): Elem = {
    <card>
      <suit>{card.getColor.toString()}</suit>
      <rank>{card.getRank.toString()}</rank>
      <isRevealed>{card.isRevealed}</isRevealed>
    </card>
  }

  def readGameField(xml: Node): GameFieldInterface = {
    val players = readPlayers((xml \ "players").head)
    val comCards = readComCards((xml \ "comCards").head)
    val playerAtTurn = (xml \ "playerAtTurn").text.toInt
    val lastRaisePlayerIdx = (xml \ "lastRaisePlayerIdx").text.toInt
    val bigBlindPlayerIdx = (xml \ "bigBlindPlayerIdx").text.toInt
    val bettingRound = (xml \ "bettingRound").text match {
      case "PREFLOP" => PREFLOP
      case "FLOP" => FLOP
      case "TURN" => TURN
      case "RIVER" => RIVER
    }
    val highestBet = (xml \ "highestBet").text.toInt

    gameFieldFactory(players, comCards, playerAtTurn, lastRaisePlayerIdx, bigBlindPlayerIdx, bettingRound, highestBet)
  }

  def writeGameField(gameField: GameFieldInterface): Elem = {
    <gameField>
      <players>
        {gameField.getPlayers.map(player => writePlayer(player))}
      </players>
      <comCards>
        {writeComCards(gameField.getCommunityCards)}
      </comCards>
      <playerAtTurn>{gameField.getPlayerAtTurn.getPlayerNum}</playerAtTurn>
      <lastRaisePlayerIdx>{gameField.getLastRaisePlayerIdx}</lastRaisePlayerIdx>
      <bigBlindPlayerIdx>{gameField.getBigBlindPlayerIdx}</bigBlindPlayerIdx>
      <bettingRound>{gameField.getBettingRound.toString}</bettingRound>
      <highestBet>{gameField.getHighestBet}</highestBet>
    </gameField>
  }
}
