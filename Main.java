import java.util.*;

// --- CLASSE BASE (ENCAPSULAMENTO) ---
abstract class Personagem {
    private String nome;
    private int vida;
    private int ataqueBase;

    public Personagem(String nome, int vida, int ataqueBase) {
        this.nome = nome;
        this.vida = vida;
        this.ataqueBase = ataqueBase;
    }

    public abstract void usarHabilidadeEspecial(Personagem alvo);

    public void receberDano(int dano) {
        this.vida -= dano;
        if (this.vida < 0) this.vida = 0;
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public int getVida() { return vida; }
    public int getAtaqueBase() { return ataqueBase; }
}

// --- HERANÇA E POLIMORFISMO ---
class Guerreiro extends Personagem {
    public Guerreiro(String nome) {
        super(nome, 120, 15);
    }

    @Override
    public void usarHabilidadeEspecial(Personagem alvo) {
        int danoEspecial = getAtaqueBase() + 15;
        System.out.println("⚔️ " + getNome() + " usou [Golpe Pesado] causando " + danoEspecial + " de dano!");
        alvo.receberDano(danoEspecial);
    }
}

class Mago extends Personagem {
    public Mago(String nome) {
        super(nome, 80, 20);
    }

    @Override
    public void usarHabilidadeEspecial(Personagem alvo) {
        int danoEspecial = getAtaqueBase() + 25;
        System.out.println("🔮 " + getNome() + " usou [Bola de Fogo] causando " + danoEspecial + " de dano!");
        alvo.receberDano(danoEspecial);
    }
}

class Monstro extends Personagem {
    public Monstro(String nome, int vida, int ataque) {
        super(nome, vida, ataque);
    }

    @Override
    public void usarHabilidadeEspecial(Personagem alvo) {
        System.out.println("👹 " + getNome() + " realizou um ataque crítico!");
        alvo.receberDano(getAtaqueBase() + 5);
    }
}

// --- CLASSE PRINCIPAL COM LOOP DE JOGO E VALIDAÇÃO ---
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> inventario = new ArrayList<>();
        inventario.add("Poção de Cura");

        System.out.println("==========================================");
        System.out.println("   ⚔️️  BEM-VINDO AO RPG QUEST CONSOLE  ⚔️   ");
        System.out.println("==========================================");
        System.out.print("Digite o nome do seu herói: ");
        String nomeHeroi = scanner.nextLine();

        System.out.println("\nEscolha sua classe:");
        System.out.println("1 - Guerreiro (Mais vida, ataque consistente)");
        System.out.println("2 - Mago (Menos vida, alto dano mágico)");
        System.out.print("Opção: ");
        
        int opcaoClasse = 1;
        try {
            opcaoClasse = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Selecionado Guerreiro por padrão.");
        }

        Personagem heroi = (opcaoClasse == 2) ? new Mago(nomeHeroi) : new Guerreiro(nomeHeroi);
        Personagem monstro = new Monstro("Goblin da Masmorra", 60, 10);

        System.out.println("\n🔥 Um " + monstro.getNome() + " apareceu no seu caminho!");

        // Loop de combate
        while (heroi.estaVivo() && monstro.estaVivo()) {
            System.out.println("\n------------------------------------------");
            System.out.println("Status: " + heroi.getNome() + " (Vida: " + heroi.getVida() + ") | " 
                               + monstro.getNome() + " (Vida: " + monstro.getVida() + ")");
            System.out.println("Ações: [1] Ataque Básico | [2] Habilidade Especial | [3] Usar Poção");
            System.out.print("Escolha sua ação: ");

            int acao = 1;
            try {
                acao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ação inválida. Executando Ataque Básico.");
            }

            switch (acao) {
                case 1 -> {
                    System.out.println("💥 " + heroi.getNome() + " atacou e causou " + heroi.getAtaqueBase() + " de dano!");
                    monstro.receberDano(heroi.getAtaqueBase());
                }
                case 2 -> heroi.usarHabilidadeEspecial(monstro);
                case 3 -> {
                    if (!inventario.isEmpty()) {
                        System.out.println("🧪 " + heroi.getNome() + " usou uma " + inventario.remove(0) + " e recuperou vida!");
                    } else {
                        System.out.println("⚠️ Inventário vazio! Turno perdido.");
                    }
                }
                default -> System.out.println("Opção inválida!");
            }

            // Turno do monstro
            if (monstro.estaVivo()) {
                System.out.println("👹 " + monstro.getNome() + " contra-ataca!");
                heroi.receberDano(monstro.getAtaqueBase());
            }
        }

        System.out.println("\n==========================================");
        if (heroi.estaVivo()) {
            System.out.println("🎉 VI T Ó R I A! Você derrotou o " + monstro.getNome() + "!");
        } else {
            System.out.println("☠️ GAME OVER! " + heroi.getNome() + " foi derrotado na masmorra.");
        }
        System.out.println("==========================================");

        scanner.close();
    }
}
