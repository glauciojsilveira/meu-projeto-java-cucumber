# AGENTS.md - Guia para Agentes de IA no Codex

## Visão Geral da Configuração de IA

Este projeto está configurado para uso com **Codex** no IntelliJ, com foco em automação de testes e fluxos assistidos por skills.

- **Ambiente principal**: IntelliJ IDEA + Codex
- **Projeto alvo**: `estudo-automacao-porto`
- **Objetivo**: apoiar desenvolvimento, documentação, validação e evolução de testes automatizados
- **Fonte de verdade para regras**: este `AGENTS.md`

---

## Fluxo de Instalação, Configuração e Uso

### Instalar skills no Codex

Use o skill de instalação quando precisar adicionar novas capacidades:

- **Skill**: `skill-installer`
- **Quando usar**:
  - listar skills instaláveis
  - instalar skill curada
  - instalar skill de repositório GitHub

### Configurar skills no repositório local

Organização recomendada:

- Skills de usuário/sistema em diretórios do Codex (fora do repositório do projeto)
- Este projeto mantém instruções operacionais em `AGENTS.md`
- Ao adicionar skill nova: documentar nome, objetivo e quando usar

### Usar no dia a dia com Codex

1. Solicitar tarefa no chat (ex: atualizar documentação, validar testes, ajustar steps)
2. Codex identifica skill aplicável (quando houver)
3. Codex executa leitura/edição/comandos locais
4. Validar resultado com Maven e arquivos gerados

---

## Agents/Skills em Uso Nesta Sessão

### Inventário Atual

```
capacity
customize
deploy-model
microsoft-foundry
preset
skill-creator
skill-installer
```

### Resumo de finalidade

- **capacity**: descoberta de capacidade/quota por região para modelos Azure OpenAI
- **customize**: deploy customizado de modelos (versão, SKU, capacidade, políticas)
- **deploy-model**: fluxo unificado para deploy de modelos no Foundry
- **microsoft-foundry**: operações fim a fim de agentes no Foundry
- **preset**: deploy rápido em região ótima automaticamente
- **skill-creator**: criação/atualização de skills
- **skill-installer**: instalação de skills

---

## Padrões e Convenções Importantes

### Regras de Uso de Skills

1. Se o pedido combinar com uma skill disponível, a skill deve ser usada
2. Abrir primeiro o `SKILL.md` da skill antes da execução
3. Carregar apenas os arquivos necessários (evitar leitura excessiva)
4. Se skill estiver indisponível, informar e seguir com fallback manual

### Convenções de Operação no Codex

- Priorizar leitura do estado real do projeto antes de editar
- Validar mudanças com comandos executáveis (`mvn test`, `mvn compile`, etc.)
- Não reverter alterações do usuário sem solicitação explícita
- Registrar ajustes relevantes em documentos `.md` do projeto

### Estratégia para Tarefas de IA no Projeto

- Documentação e análise: atualizar `.md` mantendo padrão existente
- Código de testes: preservar estrutura Cucumber/Selenium atual
- Melhoria incremental: pequenas alterações validadas por execução real

---

## Diretrizes de Atualização deste Arquivo

### Ao Adicionar Nova IA/Skill

- Incluir no inventário em **Agents/Skills em Uso Nesta Sessão**
- Descrever objetivo em 1 linha
- Informar quando usar e quando não usar

### Ao Alterar Configuração de Uso

1. Atualizar os fluxos em **Instalação, Configuração e Uso**
2. Atualizar lista de referência rápida de comandos (se necessário)
3. Manter o padrão de escrita e organização do documento

---

## Limitações Conhecidas e Configuração Futura

- Inventário de skills pode mudar por sessão
- Algumas skills dependem de contexto externo (Azure/Foundry)
- Instalação de novas skills pode exigir permissões/integrações adicionais
- Recomenda-se revisão periódica deste arquivo para manter aderência ao ambiente atual

---

## Referência Rápida de Comandos

| Tarefa | Comando |
|--------|---------|
| Rodar testes | `mvn test` |
| Compilar | `mvn clean compile` |
| Empacotar | `mvn clean package` |
| Executar app principal | `mvn exec:java -Dexec.mainClass="org.example.Main"` |
| Limpar build | `mvn clean` |

---

## Referências

- **Guia de validação**: `README_VALIDACAO.md`
- **Análise técnica atual**: `ANALISE_PASSOSDETESTE.md`
- **Dependências do projeto**: `pom.xml`
- **IDE principal**: IntelliJ IDEA com Codex


