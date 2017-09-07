package mud

class Player(private var inv: List[Item], private var roomNum: Int) {
  val help = scala.io.Source.fromFile("help.txt").mkString

  def processCommand(command: String): Unit = {
    val commands = command.toLowerCase().split(" ")

    if (commands.contains("north")) move("north");
    else if (commands.contains("south")) move("south");
    else if (commands.contains("east")) move("east");
    else if (commands.contains("west")) move("west");
    else if (commands.contains("up")) move("up");
    else if (commands.contains("down")) move("down");
    else if (commands.contains("get")) 
    {
      var itemname = commands.tail.mkString(" ").trim();
      val y = Room.rooms(roomNum).getItem(itemname);
      y match {
        case Some(item) =>
          addToInventory(item);
        case None =>
          println("There isn't anything in here")
      }
    } else if (commands.contains("drop")) {
      val x = getFromInventory(commands.tail.mkString(" ").trim())
      x match {
        case Some(item) =>
          Room.rooms(roomNum).dropItem(item)
        case None => 
          println("There aren't any items in your inventory")
      }
    }
    else if (commands.contains("inventory")) inventoryListing()
    else if (commands.contains("help")) println(help)
    else if (commands.contains("look")) println(Room.rooms(roomNum).description())
    else println("None of that nonsense")
  }

  def getFromInventory(itemName: String): Option[Item] = {
    var tempItem: Option[Item] = None
    for (i <- 0 until inv.length) {
      if (inv(i).itm == itemName) {
        tempItem = Some(inv(i))
        inv = inv.filterNot(_.itm == itemName)
      }
    }
    return tempItem
  }

  def addToInventory(item: Item): Unit = {
    inv = item :: inv
  }

  def inventoryListing(): String = {
    var names = for (i <- 0 until inv.length) yield {
      inv(i).itm
    }
    println(names.toString())
    names.toString()
  }

  def move(dir: String): Boolean = {
    var d = 0
    dir match {
      case "north" => d = 0
      case "south" => d = 1
      case "east"  => d = 2
      case "west"  => d = 3
      case "up"    => d = 4
      case "down"  => d = 5
    }
    if (Room.rooms(roomNum).getExit(d) != None) {
      roomNum = Room.rooms(roomNum).getExit(d).sum;
      println(Room.rooms(roomNum).description());
      true
    } else {
      println("You don't want to go that way")
      false
    }
  }

}
