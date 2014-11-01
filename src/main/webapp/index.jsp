<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<html>
	<head>
		<title>passwords</title>
	</head>
<body>
<center><h3>passwords services</h3></center>
Protect Your Java web site with <a href="https://dl.dropboxusercontent.com/u/688127/public-web-site/password-cloud/passwords.htm">Passwords</a><br/><br/>
<p>This site wraps access to <a href="https://dl.dropboxusercontent.com/u/688127/public-web-site/password-cloud/passwords.htm">passwords</a> JavaScript site with Java REST access.</p>
<hr/>
<p>contact me at <a HREF="mailto:ftaylor92@bc.edu">FTaylor92@BC.edu</a></p>
<p>see all <a href="http://tinyurl.com/fmt-home">my apps</a></p>
<script>
	$(document).ready(function() {
		$.ajaxSetup({ cache: false, contentType: "application/json; charset=utf-8", dataType:"json"});

		var counterUrl="http://fmtmac-bookmarks.herokuapp.com/rest/counter?site="+ encodeURIComponent(window.location.href);
		$.get(counterUrl, function(data, txtstatus, xbr) {
			$("#counter").html("<br/><br/><small><em>count: "+ data+"</em></small>");
		});
	});
</script>
<div id='counter'></div>

<div id='cloudbees'>
<hr/>
<a href="http://www.cloudbees.com"><img src="http://cloudbees.prod.acquia-sites.com/sites/default/files/styles/large/public/Button-Built-on-CB-1.png?itok=3Tnkun-C" /></a>
</div>
</body>
</html>
