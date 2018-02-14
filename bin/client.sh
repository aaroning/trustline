url="http://localhost:8080/trustline/sendFunds?recipient="
url+=$1
url+='&amount='
url+=$2
curl "$url"
