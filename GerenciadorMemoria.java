package LRU;
import java.util.Scanner;

public class GerenciadorMemoria {

    // Classe interna Page
    static class Page {
        String nome;
        long tempo; // Usado para rastrear o último acesso da página (para LRU)

        public Page(String nome) {
            this.nome = nome;
            this.tempo = System.currentTimeMillis(); // Inicializa com o tempo atual
        }

        @Override
        public String toString() {
            return nome;
        }
    }

    public static void main(String[] args) {
        // Declaração de memória RAM e HD
        Page ram[] = new Page[4]; // RAM com 4 páginas
        Page hd[] = new Page[6]; // HD com 6 páginas

        // Inicialização das páginas
        hd[0] = new Page("PARTE 1 DE A");
        hd[1] = new Page("PARTE 2 DE A");
        ram[0] = new Page("PARTE 3 DE A");

        hd[2] = new Page("PARTE 1 DE B");
        hd[3] = new Page("PARTE 2 DE B");
        hd[4] = new Page("PARTE 3 DE B");
        hd[5] = new Page("PARTE 4 DE B");

        ram[1] = new Page("PARTE 1 DE C");
        ram[2] = new Page("PARTE 2 DE C");
        ram[3] = new Page("PARTE 3 DE C");

        int opcao = -1;
        Scanner scan = new Scanner(System.in);

        while (opcao != 0) {
            System.out.println("+----------------- MENU ------------------+");
            System.out.println("| 1 - REALIZAR PAGE IN ");
            System.out.println("| 2 - REFERENCIAR QUADRO DA RAM ");
            System.out.println("| 3 - VISUALIZAR RAM E DISCO ATUALMENTE ");
            System.out.println("| 0 - SAIR ");
            System.out.println("+-----------------------------------------+");
            System.out.print("O que deseja fazer? ");
            opcao = scan.nextInt();

            int indexPageHD = -1; // Índice do HD para Page In
            int indexPageRAM = -1; // Índice da RAM para referência

            switch (opcao) {
                case 1:
                    System.out.print("Qual indice do disco sera usado no Page In? (0 a " + (hd.length - 1) + "): ");
                    indexPageHD = scan.nextInt();
                    
                    if (indexPageHD < 0 || indexPageHD >= hd.length || hd[indexPageHD] == null) {
                        System.out.println("Indice inválido ou página inexistente no HD.");
                        break;
                    }

                    System.out.println("A pagina no indice do disco selecionado: " + hd[indexPageHD]);
                    System.out.println("Selecionando pagina da RAM para substituicao usando LRU...");

                    // Implementação do Algoritmo LRU (substituir a página menos recentemente usada)
                    int indexLRU = 0;
                    long tempoMaisAntigo = Long.MAX_VALUE;

                    for (int i = 0; i < ram.length; i++) {
                        if (ram[i] != null && ram[i].tempo < tempoMaisAntigo) {
                            tempoMaisAntigo = ram[i].tempo;
                            indexLRU = i;
                        }
                    }

                    System.out.println("O indice escolhido pelo LRU para sair da RAM foi: " + indexLRU);
                    System.out.println("A pagina substituida sera: " + ram[indexLRU]);

                    // Troca entre RAM e HD
                    Page temp = ram[indexLRU];
                    ram[indexLRU] = hd[indexPageHD];
                    hd[indexPageHD] = temp;
                    ram[indexLRU].tempo = System.currentTimeMillis(); // Atualiza o tempo da nova página na RAM

                    System.out.println("Page In concluido! Pagina " + ram[indexLRU] + " foi carregada na RAM.");
                    break;

                case 2:
                    System.out.print("Qual indice da RAM sera referenciado? (0 a " + (ram.length - 1) + "): ");
                    indexPageRAM = scan.nextInt();

                    if (indexPageRAM < 0 || indexPageRAM >= ram.length || ram[indexPageRAM] == null) {
                        System.out.println("Indice invalido ou posicao vazia na RAM.");
                        break;
                    }

                    System.out.println("Referenciando pagina: " + ram[indexPageRAM]);
                    ram[indexPageRAM].tempo = System.currentTimeMillis(); // Atualiza o tempo de acesso (para LRU)
                    System.out.println("Pagina referenciada com sucesso!");
                    break;

                case 3:
                    visualizarMemoria(ram, hd);
                    break;
            }
        }
        scan.close();
    }

    public static void visualizarMemoria(Page[] ram, Page[] hd) {
        header(" MEMORIA RAM ATUAL ", ram.length);
        for (int i = 0; i < ram.length; i++) {
            System.out.print("| ");
            System.out.printf("%-15s", ram[i] != null ? ram[i] : "VAZIO");
            System.out.print(" ");
        }
        footer(ram.length);

        header(" MEMORIA HDD ATUAL ", hd.length);
        for (int i = 0; i < hd.length; i++) {
            System.out.print("| ");
            System.out.printf("%-15s", hd[i] != null ? hd[i] : "VAZIO");
            System.out.print(" ");
        }
        footer(hd.length);
    }

    public static void header(String titulo, int nElements) {
        System.out.println(titulo);
        System.out.print('+');
        System.out.print("-".repeat(17 * nElements + nElements - 1));
        System.out.print("+\n");

        for (int i = 0; i < nElements; i++) {
            System.out.print("| ");
            System.out.printf("%-15s", "Indice " + i);
            System.out.print(" ");
        }
        System.out.println("|");

        System.out.print('+');
        System.out.print("-".repeat(17 * nElements + nElements - 1));
        System.out.print("+\n");
    }

    public static void footer(int nElements) {
        System.out.println("|");
        System.out.print('+');
        System.out.print("-".repeat(18 * nElements - 1));
        System.out.println('+');
    }
}