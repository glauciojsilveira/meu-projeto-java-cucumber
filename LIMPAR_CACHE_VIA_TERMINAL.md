# 🔧 Como Limpar Cache do IntelliJ IDEA Via Terminal

## 📍 LOCALIZAÇÃO DO CACHE

O cache do IntelliJ está em pastas do sistema. A localização muda por sistema operacional:

### Windows:
```
C:\Users\{seu_usuario}\AppData\Local\JetBrains\IntelliJIdea2024\caches
C:\Users\{seu_usuario}\AppData\Local\JetBrains\IntelliJIdea2024\index
```

### Mac:
```
~/Library/Caches/JetBrains/IntelliJIdea2024
~/Library/Application Support/JetBrains/IntelliJIdea2024
```

### Linux:
```
~/.cache/JetBrains/IntelliJIdea2024
~/.config/JetBrains/IntelliJIdea2024
```

---

## 🪟 MÉTODO 1: Windows PowerShell (Recomendado para Você)

### PASSO 1: Feche o IntelliJ IDEA Completamente

```powershell
# Feche manualmente ou execute:
Get-Process IntelliJ* | Stop-Process -Force
```

### PASSO 2: Identifique a Pasta de Cache

Abra PowerShell e execute:

```powershell
# Para IntelliJ IDEA 2024:
$cacheDir = "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2024"

# Para versões mais antigas:
# $cacheDir = "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2023"
# $cacheDir = "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2022"

# Verifique se existe:
if (Test-Path $cacheDir) {
    Write-Host "✅ Pasta encontrada: $cacheDir"
} else {
    Write-Host "❌ Pasta não encontrada"
}
```

---

### PASSO 3: Limpe as Pastas (OPÇÃO A - Deletar Completo)

```powershell
# Defina a pasta
$cacheDir = "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2024"

# Remova completamente
if (Test-Path $cacheDir) {
    Remove-Item -Path $cacheDir -Recurse -Force
    Write-Host "✅ Cache deletado com sucesso!"
} else {
    Write-Host "❌ Pasta não encontrada"
}
```

---

### PASSO 4: Limpe Apenas Subpastas Específicas (OPÇÃO B - Mais Seguro)

```powershell
# Defina a pasta base
$basePath = "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2024"

# Limpe apenas caches e índices (sem deletar tudo)
$foldersToClean = @(
    "caches",
    "index",
    "system"
)

foreach ($folder in $foldersToClean) {
    $fullPath = Join-Path $basePath $folder
    if (Test-Path $fullPath) {
        Remove-Item -Path $fullPath -Recurse -Force
        Write-Host "✅ Limpou: $folder"
    }
}

Write-Host "✅ Limpeza concluída!"
```

---

### PASSO 5: Reabra o IntelliJ

```powershell
# Reabra o IntelliJ (substitua o caminho)
& "C:\Program Files\JetBrains\IntelliJ IDEA 2024.1\bin\idea64.exe"

# Ou simplesmente clique no ícone do IntelliJ
```

---

## 🚀 SCRIPT AUTOMÁTICO (Copie e Cole)

Cole isto em PowerShell:

```powershell
# Script para limpar cache do IntelliJ IDEA
Write-Host "🔧 Iniciando limpeza de cache do IntelliJ IDEA..." -ForegroundColor Green

# Feche o IntelliJ
Write-Host "⏳ Fechando IntelliJ IDEA..."
Get-Process IntelliJ* -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2

# Defina a pasta
$cacheDir = "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2024"

# Verifique se existe
if (-not (Test-Path $cacheDir)) {
    Write-Host "⚠️ Pasta não encontrada: $cacheDir"
    Write-Host "Tente uma versão diferente (2023, 2022, etc.)"
    exit
}

# Limpe as pastas principais
$folders = @("caches", "index", "system", "scratches")
foreach ($folder in $folders) {
    $path = Join-Path $cacheDir $folder
    if (Test-Path $path) {
        Write-Host "🗑️ Deletando: $folder"
        Remove-Item -Path $path -Recurse -Force -ErrorAction Continue
    }
}

Write-Host "✅ Limpeza concluída com sucesso!" -ForegroundColor Green
Write-Host "🔄 Reabra o IntelliJ IDEA"
```

---

## 📋 SCRIPT INTERATIVO (Mais Seguro)

Cole isto em PowerShell para ter confirmação:

```powershell
Write-Host "=== LIMPADOR DE CACHE - IntelliJ IDEA ===" -ForegroundColor Cyan
Write-Host ""

# Detectar versão automaticamente
$jetbrainsPath = "$env:LOCALAPPDATA\JetBrains"

if (-not (Test-Path $jetbrainsPath)) {
    Write-Host "❌ JetBrains não está instalado" -ForegroundColor Red
    exit
}

# Listar versões encontradas
Write-Host "📂 Versões encontradas:"
$versions = Get-ChildItem -Path $jetbrainsPath -Directory | Where-Object { $_.Name -like "IntelliJ*" }

if ($versions.Count -eq 0) {
    Write-Host "❌ Nenhuma pasta de IntelliJ encontrada"
    exit
}

$versions | ForEach-Object { Write-Host "  - $($_.Name)" }
Write-Host ""

# Pergunte qual versão limpar
$version = Read-Host "Qual versão deseja limpar? (Digite o nome exato)"

$cacheDir = Join-Path $jetbrainsPath $version

if (-not (Test-Path $cacheDir)) {
    Write-Host "❌ Versão não encontrada" -ForegroundColor Red
    exit
}

Write-Host ""
Write-Host "⚠️ AVISO: Isto vai deletar o cache de $version"
Write-Host "Certifique-se de ter o IntelliJ fechado!"
$confirm = Read-Host "Deseja continuar? (S/N)"

if ($confirm -ne "S") {
    Write-Host "❌ Operação cancelada"
    exit
}

Write-Host ""
Write-Host "🔧 Fechando IntelliJ..."
Get-Process IntelliJ* -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2

Write-Host "🗑️ Limpando cache..."
$folders = @("caches", "index", "system", "scratches", "log")
foreach ($folder in $folders) {
    $path = Join-Path $cacheDir $folder
    if (Test-Path $path) {
        Write-Host "   ✓ Deletando: $folder"
        Remove-Item -Path $path -Recurse -Force -ErrorAction Continue
    }
}

Write-Host ""
Write-Host "✅ Limpeza concluída!" -ForegroundColor Green
Write-Host "🔄 Reabra o IntelliJ IDEA"
```

---

## 🎯 MÉTODO 2: Linha Única (Simples)

Se quer fazer tudo em uma linha:

```powershell
# Feche e limpe de uma vez:
Get-Process IntelliJ* -ErrorAction SilentlyContinue | Stop-Process -Force; Remove-Item "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2024" -Recurse -Force -ErrorAction SilentlyContinue; Write-Host "✅ Cache limpo!"
```

---

## 📊 COMPARAÇÃO: Métodos

| Método | Tempo | Segurança | Facilidade |
|--------|-------|-----------|-----------|
| Interface Gráfica | 1 min | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| Script Automático | 30 seg | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| Script Interativo | 1 min | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| Linha Única | 10 seg | ⭐⭐⭐ | ⭐⭐ |

---

## 🆘 TROUBLESHOOTING

### "Pasta não encontrada"

Execute isto para encontrar a pasta correta:

```powershell
# Procure por qualquer pasta de IntelliJ
Get-ChildItem -Path "$env:LOCALAPPDATA\JetBrains" -Directory
```

**Resultado esperado:**
```
Mode                 LastWriteTime         Length Name
----                 -----------         ------ ----
d----        08/04/2026     10:00                IntelliJIdea2024.1
d----        08/04/2026     09:00                IntelliJIdea2023.3
```

Use a versão que encontrou.

---

### "Pasta em uso (IntelliJ aberto)"

O PowerShell não consegue deletar enquanto o IDE está aberto.

**Solução:**

```powershell
# Force o fechamento:
Get-Process | Where-Object { $_.Name -like "*IntelliJ*" -or $_.Name -like "*idea*" } | Stop-Process -Force

# Aguarde:
Start-Sleep -Seconds 3

# Tente deletar de novo
```

---

### "Acesso Negado"

Se der erro de permissão:

```powershell
# Abra PowerShell como Administrador
# Pressione: Win + X
# Clique em: Windows PowerShell (Administrador)

# Depois execute o comando
```

---

## ✅ VERIFICAÇÃO: Cache Limpou?

Execute isto para verificar:

```powershell
$cacheDir = "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2024"

if (Test-Path $cacheDir) {
    $size = (Get-ChildItem $cacheDir -Recurse | Measure-Object -Property Length -Sum).Sum
    $sizeMB = [math]::Round($size / 1MB, 2)
    Write-Host "📊 Tamanho do cache: $sizeMB MB"
} else {
    Write-Host "✅ Cache foi completamente deletado!"
}
```

---

## 🎯 RECOMENDAÇÃO

Para seu projeto, use este comando simples:

```powershell
# 1. Copie isto no PowerShell:
Get-Process IntelliJ* -ErrorAction SilentlyContinue | Stop-Process -Force; Remove-Item "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2024" -Recurse -Force -ErrorAction SilentlyContinue; Write-Host "✅ Cache limpo! Reabra o IntelliJ."; Start-Sleep -Seconds 2
```

**Resultado:**
- ✅ IntelliJ fecha
- ✅ Cache é deletado
- ✅ Você vê a mensagem de sucesso
- ✅ Pode reabrir o IntelliJ

---

## 📞 VERSÕES DO IntelliJ

Se o comando não funcionar, tente outra versão:

```powershell
# 2024:
$env:LOCALAPPDATA\JetBrains\IntelliJIdea2024

# 2023:
$env:LOCALAPPDATA\JetBrains\IntelliJIdea2023

# 2022:
$env:LOCALAPPDATA\JetBrains\IntelliJIdea2022

# 2021:
$env:LOCALAPPDATA\JetBrains\IntelliJIdea2021

# Encontre a sua versão:
Get-ChildItem "$env:LOCALAPPDATA\JetBrains" -Directory
```

---

## 🚀 PASSO A PASSO FINAL

### 1️⃣ Abra PowerShell
```
Pressione: Win + R
Digite: powershell
Pressione: Enter
```

### 2️⃣ Cole o Comando
```powershell
Get-Process IntelliJ* -ErrorAction SilentlyContinue | Stop-Process -Force; Remove-Item "$env:LOCALAPPDATA\JetBrains\IntelliJIdea2024" -Recurse -Force -ErrorAction SilentlyContinue; Write-Host "✅ Cache limpo!"; Start-Sleep -Seconds 2
```

### 3️⃣ Pressione Enter
```
Aguarde 10 segundos
```

### 4️⃣ Reabra o IntelliJ
```
Clique no ícone ou procure no Menu Iniciar
```

---

**PRONTO! Cache limpo via terminal!** ✅

