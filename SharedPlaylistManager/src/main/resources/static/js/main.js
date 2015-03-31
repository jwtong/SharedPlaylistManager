var cid = '383dfda45a098d4479d56e02d63da386';
window.onload = function() {
	// onload, need to initialize with Sound Cloud - I register myself
	// for an application so I have the client id. You could create one
	// for your own - look at the youtube coding for good (API sound cloud)
	// https://www.youtube.com/watch?v=8TuqjGxosrc

	SC.initialize({
		client_id : cid
	});
}

$(document).ready(function() {
	$('#refreshButton').click(function() {
		refresh();
	});
	
	refresh();
});

function playSomeSound(songuri) {
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

function refresh() {
	$.get("/data/playlist", function(responseJSON) {
		responseObject = JSON.parse(responseJSON);
		var data = responseObject.playlist;
		var sr = document.getElementById('playlist');
		// clean up the unordered list
		while (sr.childNodes.length > 0) {
			sr.removeChild(sr.childNodes[0]);
		}

		for (i = 0; i < data.length; i++) {
			var song = data[i];
			var elem = document.createElement("li");

			if (song.artworkURL) {
				var img = document.createElement('img');
				img.src = song.artworkURL;
				elem.appendChild(img);
			}

			elem.appendChild(document.createTextNode(song.name));

			var btnPreview = document.createElement("button");
			btnPreview.appendChild(document.createTextNode("Preview"));
			btnPreview.onclick = function(e) {
				e.preventDefault();
				playSomeSound(e.srcElement.parentNode
						.getAttribute('data-value'));
			}

			elem.appendChild(btnPreview);
			elem.setAttribute('data-value', song.uri);
			sr.appendChild(elem);
		}
	});	
}

