package de.htwg.model.gameFieldComponent

trait GameGeneratorInterface {
    def apply(numPlayers: Int): Option[GameFieldInterface]
}
