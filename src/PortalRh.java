
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PortalRh {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Funcionario> funcionarios = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println();
        System.out.println(" ==== BEM-VINDO(A) AO PORTAL RH ====");
        System.out.println();

        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> criarFuncionario();
                case 2 -> registrarHorario();
                case 3 -> listarFuncionarios();
                case 4 -> buscarFuncionario();
                case 5 -> removerFuncionario();
                case 0 -> System.out.println(
                        "Saindo do Portal RH."
                );
                default -> System.out.println(
                        "Opção inválida! Tente novamente."
                );
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1 - Criar funcionário");
        System.out.println("2 - Registrar horário");
        System.out.println("3 - Listar funcionários");
        System.out.println("4 - Buscar funcionário e ver registros");
        System.out.println("5 - Remover funcionário");
        System.out.println("0 - Sair");
        System.out.println("==========================");
    }

    private static void criarFuncionario() {
        System.out.println("\n--- Criar funcionário ---");

        int id = lerInteiro("Digite o ID: ");

        if (buscarPorId(id) != null) {
            System.out.println("Já existe um funcionário com esse ID.");
            return;
        }

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("O nome não pode ficar vazio.");
            return;
        }

        System.out.println("Escolha o tipo:");
        System.out.println("1 - Gerente");
        System.out.println("2 - Coordenador");
        System.out.println("3 - Analista");
        System.out.println("4 - Assistente");
        System.out.println("5 - Estagiário");

        int tipo = lerInteiro("Tipo: ");

        Funcionario funcionario;

        switch (tipo) {
            case 1 -> funcionario = new Gerente(id, nome);
            case 2 -> funcionario = new Coordenador(id, nome);
            case 3 -> funcionario = new Analista(id, nome);
            case 4 -> funcionario = new Assistente(id, nome);
            case 5 -> funcionario = new Estagiario(id, nome);
            default -> {
                System.out.println("Tipo inválido. Cadastro cancelado!.");
                return;
            }
        }

        funcionarios.add(funcionario);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private static void registrarHorario() {
        System.out.println("\n--- Registrar horário ---");

        int id = lerInteiro("Digite o ID do funcionário: ");
        Funcionario funcionario = buscarPorId(id);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        if (!funcionario.podeBaterPonto()) {
            System.out.println("Registro bloqueado: gerentes e estagiários " + "não batem ponto."
            );
            return;
        }

        try {
            LocalDate data = lerData("Data (AAAA-MM-DD): ");
            LocalTime entrada = lerHora("Horário de entrada (HH:mm): ");
            LocalTime saida = lerHora("Horário de saída (HH:mm): ");

            RegistroPonto registro =
                    new RegistroPonto(data, entrada, saida);

            funcionario.registrarPonto(registro);

            System.out.println("Horário registrado com sucesso!");
            registro.exibirRegistro();

        } catch (IllegalArgumentException e) {
            System.out.println("Não foi possível registrar: " + e.getMessage());
        }
    }

    private static void listarFuncionarios() {
        System.out.println("\n--- Funcionários cadastrados ---");

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        for (Funcionario funcionario : funcionarios) {
            funcionario.exibirDados();
        }
    }

    private static void buscarFuncionario() {
        System.out.println("\n--- Buscar funcionário ---");

        int id = lerInteiro("Digite o ID: ");
        Funcionario funcionario = buscarPorId(id);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        funcionario.exibirDados();

        List<RegistroPonto> registros = funcionario.getRegistros();

        if (registros.isEmpty()) {
            System.out.println("Esse funcionário não possui registros de ponto.");
            return;
        }

        System.out.println("--- Registros de ponto ---");

        for (RegistroPonto registro : registros) {
            System.out.println("---------------------------");
            registro.exibirRegistro();
        }
    }

    private static void removerFuncionario() {

           System.out.println("\n--- Remover funcionário ---");

        int id = lerInteiro("Digite o ID: ");
        Funcionario funcionario = buscarPorId(id);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        funcionarios.remove(funcionario);
        System.out.println("Funcionário removido com sucesso.");
    }

    private static Funcionario buscarPorId(int id) {
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getId() == id) {
                return funcionario;
            }
        }

        return null;

    }



    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }

    }





    private static LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            try {
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Data inválida. Use o formato AAAA-MM-DD."
                );
            }
        }
    }

    private static LocalTime lerHora(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            try {
                return LocalTime.parse(entrada);
            } catch (DateTimeParseException e) {
                System.out.println("Hora inválida. Use o formato HH:mm.");
            }
        }
    }



}

