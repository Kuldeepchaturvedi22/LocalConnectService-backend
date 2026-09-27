$ErrorActionPreference = 'Stop'
$projectRoot = $PSScriptRoot
$javaExe = Get-ChildItem -LiteralPath (Join-Path $projectRoot '.tools\jdk') -Filter 'java.exe' -Recurse |
  Where-Object { $_.FullName -like '*\bin\java.exe' } |
  Select-Object -First 1

if (-not $javaExe) { throw 'Portable Java not found under .tools\jdk.' }

$backendJar = Join-Path $projectRoot 'backend-java\target\portal-api-1.0.0.jar'
$pnpm = 'C:\Users\Lenovo\.cache\codex-runtimes\codex-primary-runtime\dependencies\bin\fallback\pnpm.cmd'
if (-not (Test-Path -LiteralPath $backendJar)) { throw 'Backend JAR missing. Build backend-java first.' }
if (-not (Test-Path -LiteralPath $pnpm)) { throw 'Bundled pnpm runtime not found.' }

$backendDir = Join-Path $projectRoot 'backend-java'
$frontendDir = Join-Path $projectRoot 'frontend-react'

if (-not (Get-NetTCPConnection -State Listen -LocalPort 8080 -ErrorAction SilentlyContinue)) {
  Start-Process -FilePath $javaExe.FullName -ArgumentList @('-jar', $backendJar) -WorkingDirectory $backendDir -WindowStyle Hidden
}
if (-not (Get-NetTCPConnection -State Listen -LocalPort 5173 -ErrorAction SilentlyContinue)) {
  Start-Process -FilePath $pnpm -ArgumentList @('run', 'dev') -WorkingDirectory $frontendDir -WindowStyle Hidden
}

Write-Host 'LocalConnectService starting...'
Write-Host 'Portal:  http://localhost:5173/'
Write-Host 'API:     http://localhost:8080/api/v1/health'
