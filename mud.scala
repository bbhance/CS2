package mud

import io.StdIn._

object Main {
  def main(args: Array[String]): Unit = {
    var input = ""
    val player = new Player(Nil, 0)
    println(Room.rooms(0).description())
    while (input != "exit") {
      input = readLine();
      player.processCommand(input)
    }
  }
}