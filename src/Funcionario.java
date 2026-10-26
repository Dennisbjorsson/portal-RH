
import java.util.ArrayList;
import java.util.List;

public abstract class Funcionario {
    private int id;
    private String nome;
    private List<RegistroPonto> registros = new ArrayList<>();

    public Funcionario(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public abstract boolean podeBaterPonto();

    public abstract int limiteHorasExtras();

    public void registrarPonto(RegistroPonto registro) {
        if (!podeBaterPonto()) {
            throw new IllegalArgumentException(
                    "Este funcionário não bate ponto."
            );
        }

        for (RegistroPonto existente : registros) {
            if (existente.getData().equals(registro.getData())) {
                throw new IllegalArgumentException(
                        "Já existe um registro para essa data."
                );
            }
        }

        if (registro.getHorasExtras() > limiteHorasExtras() * 60) {
            throw new IllegalArgumentException(
                    "Limite de horas extras ultrapassado. Limite: "
                            + limiteHorasExtras() + " horas por dia."
            );
        }

        registros.add(registro);
    }

    public List<RegistroPonto> getRegistros() {
        return new ArrayList<>(registros);
    }

    public void exibirDados() {
        System.out.println( "ID: " + id + " | Nome: " + nome + " | Tipo: " + getClass().getSimpleName()
        );
    }
}
