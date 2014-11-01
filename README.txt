ToDo:
doPost()
- in password3.htm
  - "set User Form" gets to POST and no error on response
  - "set User REST" gets to POST, but error on Response:
    - XML Parsing Error: no element found Location: moz-nullprincipal:{} Line Number 1, Column 1:


testing: http://password.fmtmac.cloudbees.net/password.htm

Testing:
-JSP
	1) goto http://password.fmtmac.cloudbees.net/rest/password?&action=GET
	2) verify that role contains manager
	3) goto http://localhost:8080/password/links/site.htm to verify that you cannot
	4) goto http://localhost:8080/password/public/site.htm to verify that you can
	5) goto http://localhost:8080/password/login.jsp, 
	6) goto http://localhost:8080/password/links/site.htm to verify that you can now
	7) goto http://localhost:8080/password/login.jsp with random name
	8) verify that you are thrown into login-fail.jsp
	
-run com.fmt.test.TestPassword
-open http://localhost:8080/password/password.htm
	1) add user w/password, site and role
	2) get role from username, password and site
	3) delete username with site
	4) check database that username no longer exists: 
TODO: 	test login JSPs
		test Filter servlet

GET role, given site, user, pass
POST user, pass, site, role - form works, JQuery ajax and post fails
DELETE user, given site

POST:
see password3.htm, watch localhost tomcat log
hit Set User REST button, and POST fails
hit Get User button, and GET works
curl -v -X POST http://localhost:8080/password/post - works

