class CacheTabManager {
	constructor(copy) {
		if(copy !== undefined){
			this.cache = Object.assign({}, copy.cache);
		} else {
			this.cache = {};
		}
	}
	
	modify(key,callback) {
		if(this.cache[key] !== undefined)
			callback(this.cache[key]);
	}

	update(key,item) {
		this.cache[key] = item;
	}
	
	get(key) {
		if(key === undefined)
			return Object.values(this.cache);
		return this.cache[key];
	}
	
	remove(key) {
		delete this.cache[key];
	}

	updateAll(callback) {
		for(let cached in this.cache){
			callback(this.cache[cached]);
		}
	}

}
export default CacheTabManager;