import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RegistroPonto {
    private LocalDate data;
    private LocalTime entrada;
    private LocalTime saida;
    private long minutosTrabalhados;
    private long horasExtras;

    private static final long MINUTOS_ALMOCO = 60;
    private static final long JORNADA_NORMAL = 8 * 60;

    public RegistroPonto(
            LocalDate data,
            LocalTime entrada,
            LocalTime saida) {

        if (saida.isBefore(entrada) || saida.equals(entrada)) {
            throw new IllegalArgumentException(
                    "A saída deve ser posterior à entrada."
            );
        }

        if (entrada.isBefore(LocalTime.of(6, 0))) {
            throw new IllegalArgumentException(
                    "A entrada não pode ser antes das 06:00."
            );
        }

        if (saida.isAfter(LocalTime.of(22, 0))) {
            throw new IllegalArgumentException(
                    "A saída não pode ser após as 22:00."
            );
        }

        long duracao = Duration.between(entrada, saida).toMinutes();

        if (duracao <= MINUTOS_ALMOCO) {
            throw new IllegalArgumentException(
                    "O período deve ser superior a 1 hora de almoço."
            );
        }

        this.data = data;
        this.entrada = entrada;
        this.saida = saida;

        this.minutosTrabalhados = duracao - MINUTOS_ALMOCO;
        this.horasExtras = Math.max(
                0, minutosTrabalhados - JORNADA_NORMAL
        );
    }

    public LocalDate getData() {
        return data;
    }

    public long getHorasExtras() {
        return horasExtras;
    }

    public void exibirRegistro() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("Data: " + data);
        System.out.println("Entrada: " + entrada.format(formato));
        System.out.println("Saída: " + saida.format(formato));
        System.out.printf(
                "Horas trabalhadas: %d hora(s) e %02d minuto(s)%n",
                minutosTrabalhados / 60,
                minutosTrabalhados % 60
        );
        System.out.printf(
                "Horas extras: %d hora(s) e %02d minuto(s)%n",
                horasExtras / 60,
                horasExtras % 60
        );
    }
}