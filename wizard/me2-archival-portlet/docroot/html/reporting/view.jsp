<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>

<portlet:defineObjects />
<h1>Archival Report Generator</h1>
<% String flag =(String)session.getAttribute("flag");
if(flag!=null &&  flag.equals("not"))
{
	out.print("<h2>No records found</h2>");
	session.removeAttribute("flag"); 
}
%> 
<portlet:resourceURL  var="exportExcelURL" id="exportExcelURL"  escapeXml="false" />
<aui:form action="<%=exportExcelURL.toString()%>"  method="post" name="date">

<aui:layout>
		<aui:column>
			<label class="aui-field-label"><font size="2">Search By</font></label>
				
		</aui:column>
		<aui:column>
							<select name="selectby">
 					 		<option value="all">all</option>
  							<option value="expired">expired</option>
 							<option value="deleted">deleted</option>
 						 	<option value="notified">notified</option>
							</select>
		</aui:column>
	</aui:layout>
	<aui:layout>
		<aui:column>
			&nbsp;
		</aui:column>
	</aui:layout>
	<aui:layout>
		<aui:column>
			<label class="aui-field-label"><font size="2">From Date</font></label>
				
		</aui:column>
		<aui:input name="startDateHidden" id="startDateHidden" type="hidden"  value="" ></aui:input>
		<aui:input name="startDateTimeHidden" id="startDateTimeHidden" type="hidden" value="" ></aui:input>
	</aui:layout>
	<aui:layout>
		<aui:column>
			<div id="myDatePicker"
				class="aui-datepicker aui-datepicker-display aui-helper-clearfix">
			</div>
		</aui:column>
	</aui:layout>
	<aui:layout>
		<aui:column>
			&nbsp;
		</aui:column>
	</aui:layout>
	
	
	<aui:layout>
		<aui:column>
			<label class="aui-field-label"><font size="2">To Date</font></label>
				
		</aui:column>
		<aui:input name="endDateHidden" id="endDateHidden" type="hidden" value="" ></aui:input>
		<aui:input name="endDateTimeHidden" id="endDateTimeHidden" type="hidden" value="" ></aui:input>
	</aui:layout>
	<aui:layout>
		<aui:column>
			<div id="myotherDatePicker"
				class="aui-datepicker aui-datepicker-display aui-helper-clearfix">
			</div>
		</aui:column>
		
	</aui:layout>
	<aui:layout>
		<aui:column>
			&nbsp;
		</aui:column>
	</aui:layout>
	
	<aui:layout>
		<aui:column>
			&nbsp;
		</aui:column>
	</aui:layout>
	<aui:layout>
		<aui:column>
			<aui:button type="button" value="Download" name="submit"
				onClick='<%=renderResponse.getNamespace()+"submitForm();"%>'></aui:button>
		</aui:column>
	</aui:layout>
	<hr/>
	<aui:layout>
<aui:column>

</aui:column>
</aui:layout>
<aui:script>
var A = AUI();
//==========================
 var startDatePicker;
 var endDatePicker;
 var myOtherDatePicker;
 
AUI().use(
  'aui-datepicker',
  function(A) {
    startDatePicker=new A.DatePickerSelect(
      {
        appendOrder: ['m', 'd', 'y'],   //dateFormat: '%m/%d/%Y', dateFormat: '%m-%d-%Y', dateFormat: '%Y:%m:%d',dateFormat: '%Y:%d:%m'
        calendar: {
        dateFormat: '%m/%d/%Y',
          
        }
      }
    ).render('#myDatePicker');
    
    
    
    //===========
    
    endDatePicker=new A.DatePickerSelect(
      {
     appendOrder: ['m', 'd', 'y'],   //dateFormat: '%m/%d/%Y', dateFormat: '%m-%d-%Y', dateFormat: '%Y:%m:%d',dateFormat: '%Y:%d:%m'
        calendar: {
        dateFormat: '%m/%d/%Y',
          
        }
      }
    ).render('#myotherDatePicker');
    
    //=====================
    
    
    

  });
  
  // you see this more details  http://alloyui.com/versions/1.5.x/api/classes/Calendar.html#attr_date

 function <portlet:namespace/>submitForm(){
	var startDate;
	var endDate;    
    A.one("#<portlet:namespace/>startDateHidden").set('value',startDatePicker.calendar.getFormattedSelectedDates ()[0]);
    A.one("#<portlet:namespace/>endDateHidden").set('value',endDatePicker.calendar.getFormattedSelectedDates ()[0]);
    
    var startDate;
startDatePicker.calendar.eachSelectedDate(function(date) {
   startDate= date.getTime();
    A.one("#<portlet:namespace/>startDateTimeHidden").set('value',startDate);
  });   
  
  endDatePicker.calendar.eachSelectedDate(function(date) {
   endDate= date.getTime();
    A.one("#<portlet:namespace/>endDateTimeHidden").set('value',endDate);
  });  
  
  //Date Comparison
  
  if(startDate>endDate){
  alert("Start Date is more than end date");
  }else{
  document.<portlet:namespace/>date.submit();
  }
  
   // alert("=============startDateHidden========"+A.one("#<portlet:namespace/>startDateHidden").get('value'));
  //  alert("=============endDateHidden========"+A.one("#<portlet:namespace/>endDateHidden").get('value'));
   // alert("=============startDateTimeHidden========"+A.one("#<portlet:namespace/>startDateTimeHidden").get('value'));
   // alert("=============endDateTimeHidden========"+A.one("#<portlet:namespace/>endDateTimeHidden").get('value'));
    

}

</aui:script>
</aui:form>