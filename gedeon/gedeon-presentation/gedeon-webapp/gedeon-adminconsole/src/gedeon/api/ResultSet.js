class ResultSet {
	/**
	*
	*/
	constructor(props){
		if(props === undefined){
			this.columns = [ 
				{"name":"id", "type":"string", "label":"Id", "isHidden":true, "isSortable":false},
				{"name":"mimetype", "type":"string", "label":"MimeType", "isStateIcon":true, "filter":"select"},
				{"name":"name", "type":"string", "label":"Name", "filter":"string"},
				{"name":"nature", "type":"string", "label":"Nature", "filter":"string"},
				{"name":"type", "type":"string", "label":"Type", "filter":"string"},
				{"name":"size", "type":"long", "label":"Content Size", "filter":"long"},
				{"name":"type1", "type":"string", "label":"Type", "filter":"string"},
				{"name":"type2", "type":"string", "label":"Type", "filter":"string"},
				{"name":"type3", "type":"string", "label":"Type", "filter":"string"},
				{"name":"type4", "type":"string", "label":"Type", "filter":"string"},
				{"name":"type5", "type":"string", "label":"Type", "filter":"string"},
				{"name":"type6", "type":"string", "label":"Type", "filter":"string"},
				{"name":"type7", "type":"string", "label":"Type", "filter":"string"},
				{"name":"type8", "type":"string", "label":"Type", "filter":"string"}
			];
			
			var item = {
					"attributes":{
						"id":"ABC123",
						"mimetype":"application/pdf",
						"name":"Test.pdf",
						"nature":"Technical",
						"type":"test",
						"size":1048576,
						"type1":"test zazzad azd zdzaz adzadzzaaz",
						"type2":"test",
						"type3":"test",
						"type4":"test",
						"type5":"test zazzad azd zdzaz adzadzzaaz",
						"type6":"test",
						"type7":"test",
						"type8":"test"
					},
					"attributesDisplay":{
						"size":"1MB"
					}
				};
			var item2 = {
					"attributes":{
						"id":"ABC1234",
						"mimetype":"image/png",
						"name":"Helloooooooooooooooooad adzzdazdad adzdzzadzazdazda.png",
						"nature":"Assumed Contractuals",
						"type":"abc",
						"size":2048,
						"type1":"test",
						"type2":"test",
						"type3":"test",
						"type4":"test",
						"type5":"test",
						"type6":"test",
						"type7":"test",
						"type8":"test"
					},
					"attributesDisplay":{
						"size":"2KB"
					}
				};
			this.items = [];
			
			/*Array.from({length:151}).forEach((obj) => {
				this.items.push(item);
				this.items.push(item2);
			});*/
			
			this.pageSize=50;
		}else {
			this.columns = props.columns ;
			this.items = props.items;
			this.pageSize = props.pageSize;
			
			if(this.columns === undefined){
				this.columns = ["Hello","Bubu"];
			}
			if(this.items === undefined){
				this.items = [["A","B"]];
			}
			if(this.pageSize === undefined){
				this.pageSize = 50;
			}
		}
	}
}
export default ResultSet