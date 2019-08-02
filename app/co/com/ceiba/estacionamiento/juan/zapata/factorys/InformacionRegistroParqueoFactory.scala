package co.com.ceiba.estacionamiento.juan.zapata.factorys

import java.sql.Timestamp

import dominio.modelos._
import dominio.contantes.Constantes._

object InformacionRegistroParqueoFactory {

  def registroParqueoCarro2Horas = InformacionVehiculoParqueadero("ALS21D", Carro, false, new Timestamp(System.currentTimeMillis() - (3600050 * 2)))

  def registroParqueoCarro5Minutos = InformacionVehiculoParqueadero("ALS21D", Carro, false, new Timestamp(System.currentTimeMillis() - (60005 * 5)))

  def registroParqueoMoto2HorasBajoCilindraje = InformacionVehiculoParqueadero("ALS21D", Motocicleta, false, new Timestamp(System.currentTimeMillis() - (3600050 * 2)))

  def registroParqueoMoto2HorasAltoCilindraje = InformacionVehiculoParqueadero("ALS21D", Motocicleta, true, new Timestamp(System.currentTimeMillis() - (3600050 * 2)))

  def registroParqueoMoto1Dia3HorasAltoCilindraje = InformacionVehiculoParqueadero("ALS21D", Motocicleta, true, new Timestamp(System.currentTimeMillis() - (3600050 * 27)))

  def registroParqueoCarro2MinutosMaximosNoRebasados = InformacionVehiculoParqueadero("ALS21D", Carro, false, new Timestamp(System.currentTimeMillis() - (3600050 * 2) + (60005 * (MinutosMaximosNuevaHora - 1))))

  def registroParqueoCarro2MinutosMaximosRebasados = InformacionVehiculoParqueadero("ALS21D", Carro, false, new Timestamp(System.currentTimeMillis() - (3600050 * 2) + (60005 * (MinutosMaximosNuevaHora + 1))))

  def registroParqueoCarroHorasMaximasParaCobroHoras = InformacionVehiculoParqueadero("ALS21D", Carro, false, new Timestamp(System.currentTimeMillis() - (3600100 * CantidadHorasMinimasCobroDia - 1)))

  def registroParqueoCarroHorasMaximasParaCobroDia = InformacionVehiculoParqueadero("ALS21D", Carro, false, new Timestamp(System.currentTimeMillis() - (3600050 * (CantidadHorasMinimasCobroDia) )))

  def registroParqueoCarro25Horas = InformacionVehiculoParqueadero("ALS21D", Carro, false, new Timestamp(System.currentTimeMillis() - (3600050 * 25)))

  def registroParqueoCarro1Dia3Horas = InformacionVehiculoParqueadero("ALS21D", Carro, false, new Timestamp(System.currentTimeMillis() - (3600050 * 27)))

}
