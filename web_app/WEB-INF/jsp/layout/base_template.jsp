<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Konga</title>
	<!--  inclusioni css e javascript -->
	<tiles:insertAttribute name="inclusions" ignore="true"/>
</head>
<body>

	<!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    	<tiles:insertAttribute name="navigation" ignore="true"/>
    </nav>
    <!--  Header -->
	<div id="divHeader" class="intro-header">
		<tiles:insertAttribute name="header" ignore="true"/>
	</div>
	
	<!-- Page Content -->
	<div id="divZolooBody">
	 <div class="content-section-b">
        <div class="container">
            <div class="row">
                <tiles:insertAttribute name="body" ignore="true"/>
            </div>
        </div>
        <!-- EO container section -->
    </div>		
	</div>
	<!-- EO Page Content -->
	
	<!-- banner -->
	<div id="divBannerFoot" class="banner">
		<tiles:insertAttribute name="banner" ignore="true"/>
	</div>
	<footer id="divFooter">
		<tiles:insertAttribute name="footer" ignore="true"/>
	</footer>
</body>
</html>