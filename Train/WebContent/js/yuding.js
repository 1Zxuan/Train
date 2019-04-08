/**
 * 
 */
function yuding(this1){
	var row=$(this1).parents('tr');
	var data=$("#dataTable").dataTable().fnGetData(row);
	alert(data.trainName);
}