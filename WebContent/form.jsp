<form action="Solve" method="get">

<label for="type">Hardware Type:</label>
<% String t = request.getParameter("type"); %>
<select id="type" name="type">
<option value="HARDDISK"<%= (t != null && t.equals("HARDDISK")) ? " selected" : "" %>>Hard Disk</option>
<option value="MOTHERBOARD"<%= (t != null && t.equals("MOTHERBOARD")) ? " selected" : "" %>>Motherboard</option>
<option value="CPU"<%= (t != null && t.equals("CPU")) ? " selected" : "" %>>CPU</option>
<option value="RAM"<%= (t != null && t.equals("RAM")) ? " selected" : "" %>>RAM (Memory)</option>
<option value="POWERSUPPLY"<%= (t != null && t.equals("POWERSUPPLY")) ? " selected" : "" %>>Power Supply</option>
</select>
<br>

<label for="max_price">Max Price:</label>
<% String mp = request.getParameter("max_price"); %>
<input type="number" name="max_price" id="max_price" value="<%= (mp == null) ? "500" : mp %>">
<br>

<label for="reliability">Cost to you if this system fails:</label>
<% String rel = request.getParameter("reliability"); %>
<input type="number" name="reliability" id="reliability" value="<%= (rel == null) ? "1000" : rel %>">
<br>

<input type="submit" value="Solve">
</form>