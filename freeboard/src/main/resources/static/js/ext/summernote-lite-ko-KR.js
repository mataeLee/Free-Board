$(document).ready(function () {
    $('#summernote').summernote({
        callbacks: {
            onImageUpload: function (files) {
                uploadSummernoteImageFile(files[0], this)
            },
            onPaste: function (e){
                var clipboardData = e.originalEvent.clipboardData
                if(clipboardData && clipboardData.items && clipboardData.items.length){
                    var item = clipboardData.items[0]
                    if(item.kind === 'file' && item.type.indexOf('image/') !== -1){
                        e.preventDefault()
                    }
                }
            }
        },
        height: 500,
        focus: true,
        toolbar: [
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['style', ['bold', 'italic', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['paragraph']],
            // ['view', ['codeview']]
        ],
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', 'Helvetica neue', 'Helvetica', 'Impact', 'Lucida Grande', 'Tahoma', 'Times New Roman', 'Verdana', 'Tahoma', 'Courier New', '맑은 고딕', '굴림', '돋움'],
        fontNamesIgnoreCheck: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', 'Helvetica neue', 'Helvetica', 'Impact', 'Lucida Grande', 'Tahoma', 'Times New Roman', 'Verdana', 'Tahoma', 'Courier New', '맑은 고딕', '굴림', '돋움'],
    });
})

function uploadSummernoteImageFile(file, editor) {
    data = new FormData();
    data.append("file", file);
    $.ajax({
        data : data,
        type : "POST",
        url : "/uploadSummernoteImageFile",
        contentType : false,
        processData : false,
        success : function(data) {
            //항상 업로드된 파일의 url이 있어야 한다.
            $(editor).summernote('insertImage', data.url);
        }
    });
}