<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Manager</title>
</head>
<body>
	<%--HEADER --%>
	<c:if test="${parent != 0}">
		<a href="?b=${parent}">..\</a>
	</c:if>
	[<a style="text-decoration: none;" href="Add">New Folder</a> |
	<a style="text-decoration: none;" href="Upload">Upload File</a>]   [<a href="Logout">Logout</a>]<br/>
	<table border="solid" style="border-collapse: collapse" width=100%>
		<tr>
			<th>Name</th>
			<th>Date</th>
			<th>Size</th>
			<th>Operations</th>
		</tr>
		<%--Starts for-loop--%>
		<c:forEach items="${listFFile}" var="file">
			<tr>
				<c:if test="${file.user == currentUser}">
					<c:if test="${file.parentId == parent}">
						<c:choose>
							<c:when test="${file.isFolder}">
								<td>
									<a style="text-decoration: none;"href="?id=${file.id}">${file.name}</a>
								</td>
							</c:when>
							<c:otherwise>
								<%--download--%>
								<td>
									<a style="text-decoration: none;"href="Download?id=${file.id}">${file.name}</a>
								</td>
							</c:otherwise>
						</c:choose>
						<td><c:out value="${file.date}"></c:out></td>
						<c:choose>
							<c:when test="${file.isFolder == false}">
								<c:choose>
									<c:when test="${file.size >= 1024 }">
										<td>
											<fmt:formatNumber type="number" maxFractionDigits="0" value="${folder.size/1024}"/> KB
										</td>
									</c:when>
									<c:otherwise>
										<td>${file.size} B</td>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td>
							<a style="text-decoration: none;"href="Edit?id=${file.id}">Rename</a> | 
							<a style="text-decoration: none;" href= "Delete?id=${file.id}">Delete</a>
						</td>
					</c:if>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</body>
</html>