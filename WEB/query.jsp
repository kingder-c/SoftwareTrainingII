<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��ʷ��ѯ</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css">
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		function doSubmit(currentPage) {
			$("#pageIndex").val(currentPage);
			$("#search").submit();
		}
		
		function goPage() {
			var goPage = $("#goPage").val();
			var totalPage = $("#totalPage").val();
			if(totalPage == 0){
				$("#pageIndex").val(1);
			}else {
				if (goPage > totalPage) {
					$("#pageIndex").val(totalPage);
				} else {
					$("#pageIndex").val(goPage);
				}
			}
			$("#search").submit();
		}
		
		function doInsert(){
			window.location.href = "<%=basePath%>StudentServlet?method=edit";
		}
		function doInsertUser(){
			var radioes = $("input[name='stu_id']:checked").val();
			if (radioes == null || radioes == undefined){
				alert("��ѡ��Ҫ�����ļ�¼��");return;
			}else {
				window.location.href = "<%=basePath%>UserServlet?method=addStudent&stu_id="+radioes;
			}
		
		}
		function doDelete(){
			var radioes = $("input[name='stu_id']:checked").val();
			if (radioes == null || radioes == undefined){
				alert("��ѡ��Ҫɾ���ļ�¼��");return;
			}else if (confirm("ȷ��Ҫɾ��ѡ���ļ�¼��\n")){
				window.location.href = "<%=basePath%>StudentServlet?method=dele&stu_id="+radioes;
			}
		}
		function doUpdate(){
			var radioes = $("input[name='stu_id']:checked").val();
			if (radioes == null || radioes == undefined){
				alert("��ѡ��Ҫ�޸ĵļ�¼��");return;
			}else{
				window.location.href = "<%=basePath%>StudentServlet?method=edit&stu_id="+radioes;
			}
		}
	
		function doAdd(){
			var radioes = $("input[name='stu_id']:checked").val();
			if (radioes == null || radioes == undefined){
				alert("����ѡ��");return;
			}else if (confirm("ȷ��Ҫѡ����\n")){
				// ����Ҫ��ֵ��ѧԱId�����ظ����ڣ��ر��Ӵ���
				var stu_id = radioes;
				$.get("StudentServlet?method=getStudentByAjax&stu_id="+stu_id, "",callback,cancel);
			}
		}
		function callback(datas){
			window.opener.getStudentInfo(datas);
            window.self.close();
		}
		function cancel(){
            window.self.close();
        }
		
	</script>
			
  </head>
  
  <body>
    <!-- ����·���� -->
	<div class="place">
		<span>λ�ã�</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">��ѯҳ��</a></li>
			<li><a href="javascript:void(0);">��ʷ��ѯ</a></li>
		</ul>
	</div>
	<div class="rightinfo">	
		<form action="<%=basePath%>StudentServlet?method=${method}" method="post" id="search" name="search">
		
			<!-- ������ -->
			<div>
				<input type="hidden" name="pageIndex" id="pageIndex" value="${currentPage }">
				<input type="hidden" name=totalPage id="totalPage" value="${totalPage }">
			</div>

			<div class="tools" >
			
			<ul class="seachform">
				<li><label>����վ�����Ͳ�ѯ</label>
					<input class="scinput" name="stu_name" type="text" value="${stu_name }" />
				</li>
				<li><label>����վ���Ų�ѯ</label>
					<select class="scinput" name="stu_graduation" type="text"/> 			
						<c:choose>
							<c:when test="${not empty stu_graduation }">
								<option value=${stu_graduation } checked>${stu_graduation }</option>
								<option value="">--�������--</option>
							</c:when>
							<c:otherwise>
								<option value="">--��ѡ��--</option>
							</c:otherwise>
						</c:choose>
						<c:forEach items="${select_stu_graduation }" var="stu">
							<c:choose>
							<c:when test="${not empty stu_graduation }">
								<option value=${stu_graduation } checked>${stu.stu_graduation }</option>
							</c:when>
							<c:otherwise>
								<option value=${stu.stu_graduation } >-${stu.stu_graduation }-</option>

							</c:otherwise>
							</c:choose>
						</c:forEach>
 					</select>
				</li>
				<!-- <li><label>��������༶��ѯ</label>
					<select class="scinput" name="cla_name" />		
						<c:choose>
							<c:when test="${not empty cla_name }">
								<option value=${cla_name } checked>${cla_name }</option>
								<option value="">--�������--</option>
								<c:forEach items="${select_cla_idList }" var="cla">
									<option value=${cla.cla_name } >-${cla.cla_name }-</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">--��ѡ��--</option>
								<c:forEach items="${select_cla_idList }" var="cla">
									<option value=${cla.cla_name } >-${cla.cla_name }-</option>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						
				 					</select>
				</li>
				<%-- <li><label>�����ڿν�ʦ��ѯ</label>
					<select class="scinput" name="cla_master" />		
						<c:choose>
							<c:when test="${not empty cla_master }">
								<option value=${cla_master } checked>${cla_master }</option>
								<option value="">--�������--</option>
								<c:forEach items="${select_tea_name }" var="tea">
									<option value=${tea.tea_name } >-${tea.tea_name }-</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">--��ѡ��--</option>
								<c:forEach items="${select_tea_name }" var="tea">
									<option value=${tea.tea_name } >-${tea.tea_name }-</option>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						
				 					</select>
				</li>	 --%>
							</ul>
							<ul class="seachform">
				<li><label>������ѧ�γ̲�ѯ</label>
					<select class="scinput" name="cou_name" />		
						<c:choose>
							<c:when test="${not empty cou_name }">
								<option value=${cou_name } checked>--${cou_name }--</option>
								<option value="">--�������--</option>
								<c:forEach items="${select_cou_name }" var="cou">
									<option value=${cou.cou_name } >${cou.cou_name }</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">--��ѡ��--</option>
								<c:forEach items="${select_cou_name }" var="cou">
									<option value=${cou.cou_name } >${cou.cou_name }</option>
								</c:forEach>
							</c:otherwise>
						</c:choose>
				 					</select>
				</li>
				<li><label>����ѧԱ״̬��ѯ</label>
					<select class="scinput" name="stu_state" />		
						<c:choose>
							<c:when test="${not empty stu_state }">
								<option value=${stu_state } checked>${stu_state }</option>
								<option value="">--�������--</option>
								<c:forEach items="${select_stu_state }" var="data">
									<option value=${data.data_num } >-${data.data_name }-</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">--��ѡ��--</option>
								<c:forEach items="${select_stu_state }" var="data">
									<option value=${data.data_num } >-${data.data_name }-</option>
								</c:forEach>
							</c:otherwise>
						</c:choose>
				 					</select>
				</li> -->
				<li><label>���ݿ�ʼʱ���ѯ</label>
					&nbsp;��&nbsp;
					<input class="scinput" id="cla_starttime1" name="cla_starttime1" type="text" value="${cla_starttime1 }" />
					<img onclick="WdatePicker({el:'cla_starttime1'})" src="My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
					&nbsp;��&nbsp;
					<input class="scinput" id="cla_starttime2" name="cla_starttime2" type="text" value="${cla_starttime2 }" />
					<img onclick="WdatePicker({el:'cla_starttime2'})" src="My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
				</li>
				<li><label>���ݽ���ʱ���ѯ</label>
					&nbsp;��&nbsp;
					<input class="scinput" id="cla_starttime1" name="cla_starttime1" type="text" value="${cla_starttime1 }" />
					<img onclick="WdatePicker({el:'cla_starttime1'})" src="My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
					&nbsp;��&nbsp;
					<input class="scinput" id="cla_starttime2" name="cla_starttime2" type="text" value="${cla_starttime2 }" />
					<img onclick="WdatePicker({el:'cla_starttime2'})" src="My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
				</li>	
				<li><input type="button" class="scbtn" value="��ѯ" onClick="doSubmit(1)" /></li>
			</ul>
			
			<c:if test="${method =='query' }">
			
				<ul style="text-align: right;" class="toolbar" >
					<li>
						<a href="javascript:void(0);" onclick="doInsert()">
						<img alt="���" src="images/t01.png"><span style="margin-top: -2px;">���</span></a>
					</li>	 
					<li>	
						<a href="javascript:void(0);" onclick="doUpdate()">
						<img alt="�޸�" src="images/t02.png"><span style="margin-top: -2px;">�޸�</span></a>
					</li>	 
					<li>	
						<a href="javascript:void(0);" onclick="doDelete()">
						<img alt="ɾ��" src="images/t03.png"><span style="margin-top: -2px;">ɾ��</span></a>
					</li>
					<!-- <li>
						<a href="javascript:void(0);" onclick="doInsertUser()">
						<img alt="����" src="images/t01.png"><span style="margin-top: -2px;">����</span></a>
					</li> -->
				</ul>
			</c:if>
			</div>
		</form>
		<c:if test="${method =='selectStudent' || method=='query' }">
			<table class="tablelist"
			style="table-layout:fixed;word-wrap:break-word;word-break:break-all;">
			<thead>
				<tr>
					<th width="4%">վ������</th>
					<th width="10%">վ����</th>
					<th width="7%">��ʼʱ��</th>
					<th width="5%">����ʱ��</th>
					<!-- <th width="20%"></th>
					<th width="20%">רҵ</th> -->
					<!-- <th width="5%"></th> -->
					<th width="10%">��ϵ�绰</th>
					<th width="auto%"></th>
					<!-- <th width="8%">Ŀǰ״̬</th>
					<th width="auto">�����˺�</th> -->
				</tr>
			</thead>
			<!-- EL���ʽ�� emptyΪ�ؼ��� �����ж϶����Ƿ�Ϊ�� -->
			<c:if test="${empty stuvList}">
				<tr class="odd">
					<td colspan="11" align="center">û�в�ѯ����ؼ�¼��</td>
				</tr>
			</c:if>
			<c:forEach items="${stuvList }" var="stuvBean">
				<tr class="odd">
					<td><input type="radio" name="stu_id" value="${stuvBean.stu_id }"></td>
					<td>${stuvBean.cla_name }</td>
					<td>
						<a href="StudentServlet?method=view&stu_id=${stuvBean.stu_id } " >
							${stuvBean.stu_name }
						</a>
					</td>
					<c:forEach items="${select_stu_sex }" var="data">
					<c:choose>
								<c:when test="${stuvBean.stu_sex==data.data_num }">
								<td>${data.data_name }</td>
								</c:when>
					</c:choose>
					</c:forEach>
					<td>${stuvBean.stu_graduation }</td>
					<td>${stuvBean.stu_major }</td>
					<%-- <td>${stuvBean.stu_education }</td> --%>
					<td>${stuvBean.stu_phone }</td>
					<td>${stuvBean.cla_starttime }</td>
					<%-- <c:forEach items="${select_stu_state }" var="data">
					<c:choose>
								<c:when test="${stuvBean.stu_state==data.data_num }">
								<td>${data.data_name }</td>
								</c:when>
					</c:choose>
					</c:forEach>
					<td>${stuvBean.user_id }</td> --%>
				</tr>
			</c:forEach>
		</table>	
			
		</c:if>
		
		<c:if test="${method =='selectStudent2' }">
			<table class="tablelist"
			style="table-layout:fixed;word-wrap:break-word;word-break:break-all;">
			<thead>
				<tr>
					<th width="8%">վ������</th>
					<th width="7%">վ����</th>
					<th width="8%">��ʼʱ��</th>
					<th width="8%">����ʱ��</th>
					
				</tr>
			</thead>
			<!-- EL���ʽ�� emptyΪ�ؼ��� �����ж϶����Ƿ�Ϊ�� -->
			<c:if test="${empty stuvList}">
				<tr class="odd">
					<td colspan="11" align="center">û�в�ѯ����ؼ�¼��</td>
				</tr>
			</c:if>
			<c:forEach items="${stuvList }" var="stuvBean">
				<tr class="odd">
					<td>${stuvBean.cla_name }</td>
					<td>
						<a href="StudentServlet?method=view&stu_id=${stuvBean.stu_id } " >
							${stuvBean.stu_name }
						</a>
					</td>
					<c:forEach items="${select_stu_sex }" var="data">
					<c:choose>
								<c:when test="${stuvBean.stu_sex==data.data_num }">
								<td>${data.data_name }</td>
								</c:when>
					</c:choose>
					</c:forEach>
					<td>${stuvBean.stu_graduation }</td>
					<td>${stuvBean.stu_major }</td>
					<%-- <td>${stuvBean.stu_education }</td> --%>
					<td>${stuvBean.cla_starttime }</td>
					<c:forEach items="${select_stu_state }" var="data">
					<c:choose>
								<c:when test="${stuvBean.stu_state==data.data_num }">
								<td>${data.data_name }</td>
								</c:when>
					</c:choose>
					</c:forEach>
						<td>
						<a href="StudentServlet?method=view&stu_id=${stuvBean.stu_id } " >
						<%-- 	<img  src="${stuvBean.stu_img }" width="150" height="200"> --%>
						</a>
						</td>
				</tr>
			</c:forEach>
		</table>	
		</c:if>

		<!-- ��ҳ���� ��ʼ -->
		<div class="pagin">
			<div>
				��&nbsp;<i class="blue">${totalNum }</i>&nbsp;����¼����ǰ��ʾ��&nbsp;<i
					class="blue">${currentPage }&nbsp;</i>ҳ&nbsp;/&nbsp;��&nbsp;<i
					class="blue">${totalPage }&nbsp;</i>ҳ&nbsp;&nbsp;
				����&nbsp;��&nbsp;<input class="goPage" type="text" id="goPage"
					name="goPage" value="${currentPage }">&nbsp;ҳ&nbsp;&nbsp; <input
					type="button" value="��ת" onclick="goPage()">
			</div>
			<div class="paginList">
				<c:if test="${currentPage == 1 }">
					<a href="javascript:void(0);">��ҳ</a>&nbsp;
					<a href="javascript:void(0);">��һҳ</a>&nbsp;
				</c:if>
				<c:if test="${currentPage > 1 }">
					<a class="blue" href="javascript:void(0);" onClick="doSubmit(1)">��ҳ</a>&nbsp;
					<a class="blue" href="javascript:void(0);"
						onClick="doSubmit(${currentPage-1})">��һҳ</a>&nbsp;
				</c:if>
				<c:if test="${currentPage >= totalPage }">
					<a href="javascript:void(0);">��һҳ</a>&nbsp;
					<a href="javascript:void(0);">βҳ</a>&nbsp;
				</c:if>
				<c:if test="${currentPage < totalPage }">
					<a class="blue" href="javascript:void(0);"
						onClick="doSubmit(${currentPage+1 })">��һҳ</a>&nbsp;
					<a class="blue" href="javascript:void(0);"
						onClick="doSubmit(${totalPage})">βҳ</a>&nbsp;
				</c:if>
			</div>
		</div>
		<!-- ��ҳ���� ��ʼ -->
		<c:if test="${method =='selectStudent' }">
				<table>
				<tr style="line-height:50px; ">
					<!-- ��ť���ɱ���ͼƬ -->
					<td>&nbsp;</td>
					<td colspan="1" align="center"><input class="button"
						type="button" name="ȷ��" value="ȷ��" onClick="doAdd()" />
					&nbsp;&nbsp;&nbsp;&nbsp;<input class="button"
						type="button" name="����" value="����" onClick="cancel()"/>
					</td><td colspan="3">&nbsp;</td>
				</tr>
				</table>
			
		</c:if>
	</div>
  </body>
</html>