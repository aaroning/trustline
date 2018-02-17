file="./src/main/resources/userRegistry.properties"

if [ -f "$file" ]
then
  echo "$file found."

  while IFS='=' read -r key value
  do
    key=$(echo $key | tr '.' '_')
    eval "${key}='${port}'"
    echo "current key is ${key}"
    if test ${key} = $1;
    then
    	break
    fi
  done < "$file"
else
  echo "$file not found."
fi

url="http://localhost:"
url+=${value}
url+="/trustline/sendFunds?recipient="
url+=$2
url+='&amount='
url+=$3
curl "$url"