
import java.util.concurrent.Executors

import aplicacion.servicios.ErrorServicio
import cats.data.{EitherT, ValidatedNel}
import infraestructura.configuracion.MensajeError
import monix.eval.Task
import monix.execution.ExecutionModel.AlwaysAsyncExecution
import monix.execution.UncaughtExceptionReporter
import monix.execution.schedulers.ExecutorScheduler

package object aplicacion {

  type FormatoEitherT[T] = EitherT[Task, MensajeError, T]

  type FormatoEither[T] = Either[MensajeError, T]

  type FormatoValidatedNel[T] = ValidatedNel[MensajeError, T]

  implicit val executionScheduler: ExecutorScheduler = ExecutorScheduler(
    Executors.newFixedThreadPool(10),
    UncaughtExceptionReporter(t => println(s"this should not happen: ${t.getMessage}")),
    AlwaysAsyncExecution
  )

  implicit class ConvertirValidatedNelAFormatoEither[T](val validated: FormatoValidatedNel[T]) extends AnyVal {
    def aFormatoEither: FormatoEither[T] =
      validated.fold(
        nonEmptyListError => Left(ErrorServicio.generarMensajeErrorUnico(nonEmptyListError.toList)),
        value => Right(value)
      )
  }

  implicit class ConvertirAFormatoEitherT[T](formatoEither: FormatoEither[T]) {
    def aFormatoEitherT: FormatoEitherT[T] = {
      EitherT.fromEither(formatoEither)
    }
  }

}
