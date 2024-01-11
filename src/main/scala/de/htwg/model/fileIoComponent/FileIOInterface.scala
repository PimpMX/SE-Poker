package de.htwg.model.fileIoComponent

import de.htwg.model.gameFieldComponent.GameFieldInterface

trait FileIOInterface {
    def load: GameFieldInterface
    def save(gameField: GameFieldInterface): Unit
}
