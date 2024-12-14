# Generate private key
openssl genrsa -out private.pem 2048

# Generate public key
openssl rsa -in private.pem -pubout -out public.pem

# Convert to environment variable format (one line, no headers)
echo "Public key:"
(Get-Content public.pem | Where-Object { $_ -notmatch "BEGIN|END" }) -join ""

echo "`nPrivate key:"
(Get-Content private.pem | Where-Object { $_ -notmatch "BEGIN|END" }) -join ""