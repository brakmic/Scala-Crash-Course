package brakmic.demo1
import java.sql.DriverManager
import java.sql.Connection

object sqldemo extends App {

    //download MSSQL driver from http://www.microsoft.com/en-us/download/details.aspx?id=11774
    //put sqljdbc4.jar into the Build Path of your Scala Project
    val driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
    val url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=NORTHWIND"
    val username = "username"
    val password = "password"
    var conn:Connection = null

    try {
      Class.forName(driver)
      conn = DriverManager.getConnection(url, username, password)

      val statement = conn.createStatement()
      val results = statement.executeQuery("SELECT * FROM Employees")
      while (results.next()) {
        val first = results.getString("FirstName")
        val last = results.getString("LastName")
        println("full name = " + first + ", " + last)
      }
    } catch {
      case e : Throwable => e.printStackTrace
    }
    conn.close()
}
