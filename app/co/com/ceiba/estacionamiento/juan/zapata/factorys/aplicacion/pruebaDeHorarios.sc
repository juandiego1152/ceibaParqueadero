//import java.sql.Timestamp
//import java.util.concurrent.TimeUnit
//
//val ValorHoraMoto = 500
//val ValorHoraCarro = 1000
//val ValorDiaCarro = 8000
//val ValorDiaMoto = 4000
//val MinutosMaximosNuevaHora = 15
//val ValorExtraPorAltoCilindraje = 2000
//
//val horaFechaActual = new Timestamp(System.currentTimeMillis())
//val horaFechaEntrada = new Timestamp(System.currentTimeMillis() - ((60005 * 16) + (3600050 * 28)))
//val resta = horaFechaActual.getTime - horaFechaEntrada.getTime
//
//val minutos = TimeUnit.MILLISECONDS.toMinutes(resta)
//
//def validarHoras(minutos: Int): Unit = {
//  val horas = (minutos / 60)
//  println("horas " +horas)
//  val minutosRestantes = minutos - (horas * 60)
//  println("minutosRestantes " +minutosRestantes)
//  val diasServicio = horas / 24
//  println("diasServicio " +diasServicio)
//  val horasServicio = horas - (24 * diasServicio)
//
//  if (minutosRestantes > MinutosMaximosNuevaHora)
//    println(s"Tiempo: $diasServicio día(s), ${horasServicio + 1} horas (1)" )
//  else
//    println(s"Tiempo: $diasServicio dia(s), ${horasServicio} horas (2)")
//}
//
//validarHoras(minutos.toInt)
//def calcularDiasServicioParqueoPrestado(horasServicio: Int): Int = {
//  if (horasServicio > 6 && horasServicio < 25) 1
//  else if (horasServicio > 5) {
//    1 + calcularDiasServicioParqueoPrestado(horasServicio-24)
//  }
//  else 0
//}
//
//calcularDiasServicioParqueoPrestado(29)
//
