const CACHE='tank-arena-classic-v41';
const ASSETS=['/','/index.html','/styles.css','/game.js','/core.js','/icon.svg','/manifest.webmanifest','/privacy.html','/support.html'];
self.addEventListener('install',e=>e.waitUntil(caches.open(CACHE).then(c=>c.addAll(ASSETS)).then(()=>self.skipWaiting())));
self.addEventListener('activate',e=>e.waitUntil(caches.keys().then(keys=>Promise.all(keys.filter(k=>k!==CACHE).map(k=>caches.delete(k)))).then(()=>self.clients.claim())));
self.addEventListener('fetch',e=>{if(e.request.url.includes('/api/')||e.request.method!=='GET')return;const url=new URL(e.request.url);e.respondWith(fetch(e.request,{cache:'no-store'}).then(r=>{if(r.ok&&url.origin===location.origin){const c=r.clone();caches.open(CACHE).then(x=>x.put(e.request,c));}return r;}).catch(()=>caches.match(e.request).then(r=>r||caches.match('/index.html'))));});
