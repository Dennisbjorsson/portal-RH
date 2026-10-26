public class Assistente extends Funcionario {

    public Assistente(int id, String nome) {
        super(id, nome);
    }

    @Override
    public boolean podeBaterPonto() {
        return true;
    }

    @Override
    public int limiteHorasExtras() {
        return 3;
    }
}