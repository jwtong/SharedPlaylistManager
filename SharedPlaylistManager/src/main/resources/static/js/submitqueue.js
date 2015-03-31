var sm;
function playSomeSound(songuri) {
	// songuri is the node the stream url
	//var lblText = document.getElementById('mytext');

	// use SC.stream to retreive the stream
	SC.stream(songuri, function(sound) {
		// callback from the stream call
		// the callback gives a sound manager oject which
		// can be controlled to play the stream content.
		// However it also has the uri of the stream and
		// you can use that for your own good. In here,
		// I don't use the Sound Manager but extract
		// the URL and use the standard html audio
		// built-in to play the song. Both sm and audio
		// should have event that let you know when the
		// song play is finished. For your application,
		// you could use this webpage to preview (listen)
		// on the client browser (it might work for the
		// cell phone) before submitting it to your
		// server to play on the speaker of the server
		var audio = document.getElementById('ap');
		audio.src = sound.url;

		audio.load(); // call this to just preload the audio without playing
		audio.play(); // call this to play the song right away

	});
}

var cid = '383dfda45a098d4479d56e02d63da386';
window.onload = function() {
	// onload, need to initialize with Sound Cloud - I register myself
	// for an application so I have the client id. You could create one
	// for your own - look at the youtube coding for good (API sound cloud)
	// https://www.youtube.com/watch?v=8TuqjGxosrc

	SC.initialize({
		client_id : cid
	});
	document.getElementById('searchButton').onclick = function(e) {
		e.preventDefault();
		searchSong();
	}
}

function searchSong() {
	var si = document.getElementById('searchInput');

	// si.value is the text put, SC.get will the information
	// of top close matches (in Sound Cloud public song list).
	SC.get('/tracks', {
		q : si.value,
	}, function(tracks) {
		// callback when the list return
		var sr = document.getElementById('searchResult');

		// clean up the unordered list
		while (sr.childNodes.length > 0) {
			sr.removeChild(sr.childNodes[0]);
		}
		
		tracks.sort(dynamicSort("-playback_count"));

		// one by one; add the song name and the uri
		// ** using the debugger could see that each
		// track object returns tons of information
		for (var i = 0; i < tracks.length; i++) {
			var t = tracks[i];

			// create the queue button
			var btnQueue = document.createElement("button");
			btnQueue.appendChild(document.createTextNode("Queue"));
			btnQueue.onclick = function(e) {
				e.preventDefault();
				var jdata = {
					name : e.srcElement.parentNode.getAttribute('name-value'),
					uri : e.srcElement.parentNode
							.getAttribute('data-value'),
					artworkURL : e.srcElement.parentNode
					.getAttribute('artworkURL-value')
				};

				$.post("/data/queue", jdata, function(responseJSON) {
					responseObject = JSON.parse(responseJSON);
					var ls = responseObject.listsize;
					console.log(ls);
				})

			}

			// preview button
			var btnPreview = document.createElement("button");
			btnPreview.appendChild(document.createTextNode("Preview"));
			btnPreview.onclick = function(e) {
				e.preventDefault();
				playSomeSound(e.srcElement.parentNode
						.getAttribute('data-value'));
			}

			// create a li and add data to it
			var elem = document.createElement("li");

			// li data-value attribute store track uri (track uri is not the
			// stream uri)
			elem.setAttribute('name-value', t.title);
			elem.setAttribute('data-value', t.uri);

			if (t.artwork_url) {
                var img = document.createElement('img');
            	img.src = t.artwork_url;
            	elem.appendChild(img);
    			elem.setAttribute('artworkURL-value', t.artwork_url);
            }

			elem.appendChild(document.createTextNode(t.title));
			elem.appendChild(btnPreview);
			elem.appendChild(btnQueue);
			sr.appendChild(elem);
		}
		;
	});
	
	function dynamicSort(property) {
	    var sortOrder = 1;
	    if(property[0] === "-") {
	        sortOrder = -1;
	        property = property.substr(1);
	    }
	    return function (a,b) {
	        var result = (a[property] < b[property]) ? -1 : (a[property] > b[property]) ? 1 : 0;
	        return result * sortOrder;
	    }
	}
}
