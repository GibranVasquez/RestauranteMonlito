param(
    [string]$JavaHome = ""
)

$ErrorActionPreference = "Stop"

if (-not $JavaHome) {
    try {
        $javaExe = (Get-Command java -ErrorAction Stop).Source
        $candidateHome = Split-Path (Split-Path $javaExe -Parent) -Parent
        if (Test-Path $candidateHome) {
            $JavaHome = $candidateHome
        }
    }
    catch {
        # Se valida abajo con mensaje amigable.
    }
}

if (-not $JavaHome -or -not (Test-Path $JavaHome)) {
    Write-Error "No se pudo resolver JAVA_HOME. Pasa uno valido: .\run-services.ps1 -JavaHome 'C:\\ruta\\jdk-21'"
    exit 1
}

$root = $PSScriptRoot

$services = @(
    @{ Name = "Eureka";      Path = "eureka-server" },
    @{ Name = "Clientes";    Path = "servicio-clientes" },
    @{ Name = "Productos";   Path = "servicio-productos" },
    @{ Name = "Pedidos";     Path = "servicio-pedidos" },
    @{ Name = "Pagos";       Path = "servicio-pagos" },
    @{ Name = "Entregas";    Path = "servicio-entregas" },
    @{ Name = "Gateway";     Path = "api-gateway" }
)

foreach ($svc in $services) {
    $modulePath = Join-Path $root $svc.Path

    if (-not (Test-Path $modulePath)) {
        Write-Warning "No existe el modulo: $modulePath"
        continue
    }

    $cmd = @(
        "$env:JAVA_HOME='$JavaHome'",
        "$env:Path='$JavaHome\\bin;' + $env:Path",
        "Set-Location '$modulePath'",
        "..\\mvnw.cmd spring-boot:run"
    ) -join "; "

    Start-Process powershell -ArgumentList @(
        "-NoExit",
        "-Command",
        $cmd
    )

    Write-Host "Iniciado: $($svc.Name) ($($svc.Path))"
    Start-Sleep -Seconds 2
}

Write-Host "Se lanzaron todas las terminales. Espera a que Eureka suba antes de validar rutas del Gateway."
