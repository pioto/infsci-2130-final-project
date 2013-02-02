<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="edu.pitt.mwk21.infsci2130.finalproject.*,java.util.Iterator,java.text.DecimalFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>INFSCI 2130 - Final Project</title>
</head>
<body>

<h1>INFSCI 2130 - Final Project</h1>
<h2>Mike Kelly &amp; Michelle Neustein</h2>

<%@ include file="form.jsp" %>

<hr>

<h3>Results:</h3>

<p>
  <em>Rating</em>s are given the average on a 1-5 scale.
</p>

<p>
  <em>"Real Cost"</em> is the cost including the expected cost of the device failing.
</p>

<table border="1">

<tr>
<th>Description</th>
<th>Price</th>
<th>Rating</th>
<th>"Real Cost"</th>
</tr>

<%
Results results = (Results)request.getAttribute("results");
Iterator<Result> i = results.iterator();
DecimalFormat ratingFormatter = new DecimalFormat("#0.00");
while (i.hasNext()) {
	Result r = i.next();
	Hardware h = r.getHardware();
%>
<tr>
<td><a href="<%= h.getUrl() %>"><%= h.getDescription() %></a></td>
<td><%= h.getPrice() %></td>
<td><%= ratingFormatter.format(h.getRating().getAverage()) %>
<td><%= r.getRealPrice() %>
</tr>
<%
}
%>
</table>

<p><a href="<%= request.getAttribute("downloadURL") %>">Download</a> the model.</p>

</body>
</html>