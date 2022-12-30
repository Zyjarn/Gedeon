class UserPreferences {
	
	const defaultPrefs = {
		
	}
	
	let cache = {};
	
	function add(key,item){
		cache[key] = item;
	}
	
	function get(key){
		return cache[key] = item;
	}
	
	function remove(key){
		delete cache[key];
	}
}
export default useCachedTab;