# Generate private key
openssl genrsa -out private.pem 2048

# Generate public key from private key
openssl rsa -in private.pem -pubout -out public.pem

# Remove headers/footers and whitespace in one go
# SECURITY NOTE: Consider redirecting output to files instead of displaying in terminal
PUBLIC_KEY=$(cat public.pem | grep -v "BEGIN PUBLIC KEY" | grep -v "END PUBLIC KEY" | tr -d '\n')
PRIVATE_KEY=$(cat private.pem | grep -v "BEGIN PRIVATE KEY" | grep -v "END PRIVATE KEY" | tr -d '\n')

# Print the cleaned keys
# SECURITY RISK: Printing private key to terminal (could be seen in history/logs)
echo "public-key: $PUBLIC_KEY"
echo "private-key: $PRIVATE_KEY"
