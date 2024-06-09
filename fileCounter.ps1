param(
    [string]$FolderPath
)

chcp 1250

$extensions = @(".js", ".jsx", ".java")
$files = Get-ChildItem $FolderPath -Recurse -File | Where-Object { $extensions -contains $_.Extension }

$fileCount = $files.Count
$lineCount = 0

foreach ($file in $files) {
    $lineCount += (Get-Content $file.FullName | Measure-Object -Line).Lines
}

Write-Host "Liczba plikow w folderze $FolderPath i jego podfolderach: $fileCount"
Write-Host "Liczba linii w plikach $FolderPath i jego podfolderach: $lineCount"
