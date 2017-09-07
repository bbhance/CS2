package mud

import scala.io.Source

class Room(name: String, desc: String, private var items: List[Item], private var exit: Array[Int]) {
  var exitNames = List.apply("north", "south", "east", "west", "up", "down")

  def description(): String = {
    name + "\n" + desc + "\n" + 
    "Exits: " + exitNames.zip(exit).filter(_._2 != -1).map(_._1).mkString(",") +"\n" + 
    "Items: " + items.map(_.itm).mkString(",");
  }

  def getExit(dir: Int): Option[Int] = {
    if (exit(dir) == -1) None else (Some(exit(dir)))
  }

  def getItem(ItemName: String): Option[Item] = {
    val copy = items.filter(_.itm == ItemName).headOption
    items = items.filterNot(_.itm == ItemName)
    copy
  }

  def dropItem(item: Item): Unit = {
    items = item :: items
  }

}

object Room {
  val rooms = readRoomsFromFile()

  def readRoomsFromFile(): Array[Room] = {
    val source = io.Source.fromFile("maze.txt");
    val lines = source.getLines();
    val numOfRooms = lines.next.toInt;
    val r = Array.fill(numOfRooms)(readRoom(lines));
    source.close;
    r;
  }

  def readRoom(lines: Iterator[String]): Room = {
    val name = lines.next();
    val desc = lines.next();
    val items = List.fill(lines.next.toInt) {
      val itm = lines.next.split(";")
      new Item(itm(0), itm(1))
    }
    val exits = lines.next().split(" ").map(_.toInt);
    new Room(name, desc, items, exits);
  }

}