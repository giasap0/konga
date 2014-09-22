
//
// Simple rich user experience - jquery.fileDownload.js & jQuery UI Dialog
//
//      the below uses jQuery "on" http://api.jquery.com/on/ (jQuery 1.7 + required) so that any 
//      <a class="fileDownloadWithLoadingScreen..."/> that is ever loaded into an Ajax site will automatically use jquery.fileDownload.js
//
$(document).on("click", "a.fileDownloadWithLoadingScreen", function () {
    $.fileDownload($(this).prop('href'), {
        preparingMessageHtml: "We are preparing your file's download, please wait...",
        failMessageHtml: "There was a problem generating your file, please try again."
    });
    return false; //this is critical to stop the click event which will trigger a normal file download!
});
