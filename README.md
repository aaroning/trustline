A solution to the trustline problem:  
https://gist.github.com/sappenin/4e649235cc33f83c743801696a702ae3  
  
The project uses jersey, and can use either Guice or Spring for dependency injection (see https://github.com/aaroning/trustline/blob/master/src/main/java/com/trustline/runtime/Trustline.java#L40).  
  
The service has a REST endpoint:  
**/trustline/account**  
which takes a JSON payload which represents the amount of debt to be sent. A convenience endpoint is also provided for invoking the account resource on another server.  
  
The premise is that you if you have your own trustline server running, you can use it to send debt to another server (as well as receive payments). This convenience endpoint could just as easily be replaced with a shell script wrapper around a CURL command like:  
**curl -X POST -H "content-type: application/json" -d '{"amount": 69}' http://localhost:8081/trustline/account**  
  
The following scripts have been created in the bin directory:    
  
**start-server.sh {port}**  
*This starts a server on a given port*    
  
**stop-server.sh {port}**  
*This stops the server running on a given port*  
  
**client.sh {sender} {recipient} {amount}**  
*This sends debt between the 2 parties. This uses an convenience endpoint on the sender's server, but as said above, it could be replaced by a CURL command or a thin client that's built as a different build product.*  
  
Integration test (run by failsafe plugin) redirects the standard outut to a file and verifies against that.  
  
Any questions or suggestions for improvement please contact me at aaron@ingber.net.  
