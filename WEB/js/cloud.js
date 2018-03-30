
// Cloud Float...
    var $main = $cloud = mainwidth = null;
    var offset1 = 450;
	var offset2 = 300;
    var offset3 = 800;
    var offset4 = 0;
	
	var offsetbg = 0;
    
    $(document).ready(
        function () {
            $main = $("#mainBody");
			$body = $("body");
            $cloud1 = $("#cloud1");
			$cloud2 = $("#cloud2");
            $cloud3 = $("#cloud3");
            $cloud4 = $("#cloud4");
			
            mainwidth = $main.outerWidth();
         
        }
    );

    /// 飘动
    setInterval(function flutter() {
        if (offset1 >= mainwidth) {
            offset1 =  -580;
        }

        if (offset2 >= mainwidth) {
			 offset2 =  -580;
        }

        if (offset3 >= mainwidth) {
             offset3 =  -580;
        }

        if (offset4 >= mainwidth) {
             offset4 =  -580;
        }
		
        offset1 += 0.9;
		offset2 += 1;
        offset3 += 1.3;
        offset4 += 0.8;
        $cloud1.css("background-position", offset1 + "px 100px")
		
		$cloud2.css("background-position", offset2 + "px 460px")

        $cloud3.css("background-position", offset3 + "px 200px")

        $cloud4.css("background-position", offset4 + "px 300px")

    }, 70);
	
	
	setInterval(function bg() {
        if (offsetbg >= mainwidth) {
            offsetbg =  -580;
        }

        offsetbg += 0.9;
        $body.css("background-position", -offsetbg + "px 0")
    }, 90 );
	