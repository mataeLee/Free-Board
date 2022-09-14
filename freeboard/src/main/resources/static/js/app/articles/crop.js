const canvas = document.getElementById('article-canvas')
const context = canvas.getContext('2d')

var image = new Image()

const targetWidth = 600
const targetHeight = 600


function setArticleCanvasImageSrc(){
    const fileReader = new FileReader()
    fileReader.readAsDataURL(document.getElementById('article-input-img').files[0])
    fileReader.onload = (e) => {
        context.clearRect(0, 0, canvas.width, canvas.height);
        image.src = e.target.result;
        console.log('image src is ' + image.src)

    }
}

image.onload = function (){
    // upload된 image의 사이즈를 원하는 비율로 증가(600, 600)
    // ex) img width = 200, height = 300 -> 600, 900
    console.log('origin width ' + this.width + ', height ' + this.height)
    iWidth = this.width
    iHeight = this.height

    if(iWidth < targetWidth || iHeight < targetHeight){
        var multiple = 1
        if(iWidth < iHeight){
            multiple = Math.ceil((targetWidth / iWidth) * 10 ) / 10
        }
        else{
            multiple = Math.ceil((targetWidth / iWidth) * 10 ) / 10
        }

        console.log('multiple value : ' + multiple)

        image.width = iWidth * multiple
        image.height = iHeight * multiple
    }

    console.log('image width : ' + image.width + ', height : ' + image.height)

    var sx = (image.width - targetWidth) / 2
    var sy = (image.height - targetHeight) / 2

    console.log('sx : ' + sx + ', sy : ' + sy)
    // context.drawImage(image, 0, 0)
    context.drawImage(image, 0, 0, image.width, image.height)
    // context.drawImage(image, sx, sy,  600, 600)
}
