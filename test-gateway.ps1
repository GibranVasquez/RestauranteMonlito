$ErrorActionPreference = "Stop"

$base = "http://localhost:8080"
$paths = @(
    "/clientes",
    "/productos",
    "/categorias",
    "/pedidos",
    "/metodos-pago",
    "/entregas"
)

foreach ($path in $paths) {
    $url = "$base$path"
    try {
        $response = Invoke-WebRequest -Uri $url -Method Get -UseBasicParsing
        Write-Host "OK $($response.StatusCode) $url"
    }
    catch {
        if ($_.Exception.Response) {
            $status = [int]$_.Exception.Response.StatusCode
            Write-Host "ERR $status $url"
        }
        else {
            Write-Host "ERR ??? $url - $($_.Exception.Message)"
        }
    }
}
