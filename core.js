export const VERSION = '4.0.0';
export const WORLD = {
  w: 1600,
  h: 900,
  groundStep: 4,
  gravity: 255,
  windAccel: 18,
  tankW: 66,
  tankH: 34,
  turnSeconds: 35,
  turnFuel: 42,
};

export const WEAPONS = {
  classic: {
    id: 'classic',
    name: 'Classic Shell',
    damage: 35,
    radius: 72,
    crater: 38,
    speed: 7.2,
    behavior: 'normal',
  },
  heavy: {
    id: 'heavy',
    name: 'Titan Shell',
    damage: 53,
    radius: 102,
    crater: 62,
    speed: 5.65,
    behavior: 'normal',
  },
  cluster: {
    id: 'cluster',
    name: 'Storm Cluster',
    damage: 18,
    radius: 43,
    crater: 22,
    speed: 6.75,
    behavior: 'cluster',
  },
  napalm: {
    id: 'napalm',
    name: 'Pyro Flame',
    damage: 27,
    radius: 74,
    crater: 26,
    speed: 6.55,
    behavior: 'napalm',
  },
  drill: {
    id: 'drill',
    name: 'Mole Drill',
    damage: 46,
    radius: 67,
    crater: 55,
    speed: 6.9,
    behavior: 'drill',
  },
  ricochet: {
    id: 'ricochet',
    name: 'Comet Bounce',
    damage: 39,
    radius: 68,
    crater: 35,
    speed: 7.55,
    behavior: 'ricochet',
  },
};

export const TANKS = {
  classic: {
    id: 'classic',
    name: 'CLASSIC',
    weapon: 'classic',
    color: '#4f9cf2',
    accent: '#76d4ff',
    hp: 100,
    move: 1,
  },
  tiger: {
    id: 'tiger',
    name: 'TIGER',
    weapon: 'heavy',
    color: '#f2a22d',
    accent: '#ffd96a',
    hp: 112,
    move: 0.82,
  },
  storm: {
    id: 'storm',
    name: 'STORM',
    weapon: 'cluster',
    color: '#7655e8',
    accent: '#c6b4ff',
    hp: 94,
    move: 1.12,
  },
  pyro: {
    id: 'pyro',
    name: 'PYRO',
    weapon: 'napalm',
    color: '#ed5a43',
    accent: '#ffb35c',
    hp: 98,
    move: 1.02,
  },
  mole: {
    id: 'mole',
    name: 'MOLE',
    weapon: 'drill',
    color: '#4fba7b',
    accent: '#9de1a8',
    hp: 104,
    move: 0.94,
  },
  comet: {
    id: 'comet',
    name: 'COMET',
    weapon: 'ricochet',
    color: '#42b8c5',
    accent: '#9ff3ee',
    hp: 96,
    move: 1.08,
  },
};
export const TANK_ORDER = Object.keys(TANKS);

export function normalizeTank(id) {
  return TANKS[id] ? id : 'classic';
}
export function tankWeapon(tank) {
  return WEAPONS[TANKS[normalizeTank(tank.tankType)]?.weapon] || WEAPONS.classic;
}
export function hashSeed(text) {
  let h = 2166136261 >>> 0;
  for (const c of String(text)) {
    h ^= c.charCodeAt(0);
    h = Math.imul(h, 16777619);
  }
  return h >>> 0;
}
export function rand(state) {
  let x = state.rng >>> 0;
  x ^= x << 13;
  x ^= x >>> 17;
  x ^= x << 5;
  state.rng = x >>> 0;
  return state.rng / 4294967296;
}
export function randRange(state, a, b) {
  return a + (b - a) * rand(state);
}

export function makeTerrain(seed) {
  const temp = { rng: seed || 1 };
  const n = Math.ceil(WORLD.w / WORLD.groundStep) + 1;
  const out = [];
  const phase = randRange(temp, 0, Math.PI * 2);
  const phase2 = randRange(temp, 0, Math.PI * 2);
  for (let i = 0; i < n; i++) {
    const x = i * WORLD.groundStep;
    const base = 565 + 55 * Math.sin(x / 220 + phase) + 28 * Math.sin(x / 91 + phase2) + 9 * Math.sin(x / 42);
    const edge = 35 * Math.pow(Math.abs(x - WORLD.w / 2) / (WORLD.w / 2), 1.55);
    out.push(Math.max(430, Math.min(675, base + edge)));
  }
  for (let k = 0; k < 6; k++) {
    for (let i = 1; i < n - 1; i++) out[i] = (out[i - 1] + out[i] * 3 + out[i + 1]) / 5;
  }
  return out;
}
export function terrainY(state, x) {
  const p = Math.max(0, Math.min(state.terrain.length - 1, x / WORLD.groundStep));
  const a = Math.floor(p);
  const b = Math.min(state.terrain.length - 1, a + 1);
  const t = p - a;
  return state.terrain[a] * (1 - t) + state.terrain[b] * t;
}
export function terrainSlope(state, x) {
  return (terrainY(state, x + 14) - terrainY(state, x - 14)) / 28;
}
export function settleTank(state, tank) {
  tank.x = Math.max(38, Math.min(WORLD.w - 38, tank.x));
  tank.y = terrainY(state, tank.x) - WORLD.tankH / 2 - 4;
  tank.rotation = Math.atan(terrainSlope(state, tank.x));
}
function makeTank(id, name, x, type) {
  const tankType = normalizeTank(type);
  const spec = TANKS[tankType];
  return {
    id,
    name: name || (id === 0 ? 'Player' : 'Opponent'),
    tankType,
    x,
    y: 0,
    hp: spec.hp,
    maxHp: spec.hp,
    angle: id === 0 ? 45 : 135,
    power: 62,
    fuel: WORLD.turnFuel,
    alive: true,
    rotation: 0,
  };
}
export function createState(seed = Date.now(), names = ['Player', 'Opponent'], tankTypes = ['classic', 'classic']) {
  const s = {
    version: VERSION,
    seed: seed >>> 0,
    rng: (seed ^ 0x9e3779b9) >>> 0,
    terrain: makeTerrain(seed >>> 0),
    tanks: [makeTank(0, names[0], 230, tankTypes[0]), makeTank(1, names[1], 1370, tankTypes[1])],
    turn: 0,
    turnNumber: 1,
    turnEndsAt: 0,
    wind: 0,
    phase: 'aim',
    projectiles: [],
    fires: [],
    events: [],
    winner: null,
    shotSerial: 0,
  };
  s.wind = Math.round(randRange(s, -6, 6));
  s.tanks.forEach(t => settleTank(s, t));
  return s;
}
export function cloneState(s) { return JSON.parse(JSON.stringify(s)); }
export function packState(s) { return JSON.stringify(s); }
export function unpackState(v) {
  const s = typeof v === 'string' ? JSON.parse(v) : v;
  if (!s || s.version !== VERSION || !Array.isArray(s.terrain) || !Array.isArray(s.tanks)) throw new Error('Invalid snapshot');
  return s;
}

export function canMove(state, player, dir) {
  if (state.phase !== 'aim' || state.turn !== player || ![-1, 1].includes(dir)) return false;
  const t = state.tanks[player];
  if (t.fuel <= 0 || !t.alive) return false;
  const nx = t.x + dir * 6;
  if (nx < 40 || nx > WORLD.w - 40) return false;
  const dy = Math.abs(terrainY(state, nx + dir * 20) - terrainY(state, nx - dir * 20));
  if (dy > 38) return false;
  const other = state.tanks[1 - player];
  if (Math.abs(nx - other.x) < WORLD.tankW + 16) return false;
  return true;
}
export function moveTank(state, player, dir, dt) {
  if (!canMove(state, player, dir)) return 0;
  const t = state.tanks[player];
  const spec = TANKS[normalizeTank(t.tankType)];
  const maxDx = Math.min(118 * spec.move * dt, t.fuel / 0.78);
  const before = t.x;
  t.x += dir * maxDx;
  settleTank(state, t);
  t.fuel = Math.max(0, t.fuel - Math.abs(t.x - before) * 0.78);
  return t.x - before;
}
export function muzzle(tank) {
  const r = tank.angle * Math.PI / 180;
  return { x: tank.x + Math.cos(r) * 48, y: tank.y - Math.sin(r) * 48 };
}
export function initialVelocity(tank) {
  const weapon = tankWeapon(tank);
  const r = tank.angle * Math.PI / 180;
  const v = tank.power * weapon.speed;
  return { vx: Math.cos(r) * v, vy: -Math.sin(r) * v };
}
export function simulateTrajectory(state, tank, max = 240, dt = 0.045) {
  const weapon = tankWeapon(tank);
  const p = muzzle(tank);
  const v = initialVelocity(tank);
  const points = [];
  let x = p.x, y = p.y, vx = v.vx, vy = v.vy, bounced = false;
  for (let i = 0; i < max; i++) {
    vx += state.wind * WORLD.windAccel * dt;
    vy += WORLD.gravity * dt;
    x += vx * dt;
    y += vy * dt;
    if (i % 3 === 0) points.push({ x, y });
    if (x < 0 || x > WORLD.w || y > WORLD.h) break;
    if (y >= terrainY(state, x)) {
      if (weapon.behavior === 'ricochet' && !bounced) {
        bounced = true;
        y = terrainY(state, x) - 5;
        vy = -Math.abs(vy) * 0.6;
        vx *= 0.82;
      } else break;
    }
  }
  return points;
}
export function predictedImpact(state, tank) {
  const pts = simulateTrajectory(state, tank, 320, 0.035);
  return pts[pts.length - 1] || muzzle(tank);
}
export function fire(state, player) {
  if (state.phase !== 'aim' || state.turn !== player || state.winner !== null) return false;
  const tank = state.tanks[player];
  const weapon = tankWeapon(tank);
  const m = muzzle(tank);
  const v = initialVelocity(tank);
  state.projectiles.push({
    id: ++state.shotSerial,
    owner: player,
    weapon: weapon.id,
    x: m.x,
    y: m.y,
    vx: v.vx,
    vy: v.vy,
    age: 0,
    bounced: false,
    split: false,
    dead: false,
  });
  state.phase = 'projectile';
  state.events.push({ type: 'fire', player, weapon: weapon.id, tankType: tank.tankType });
  return true;
}
function nowMs() { return Date.now(); }
export function deformTerrain(state, x, radius, depth) {
  const a = Math.max(0, Math.floor((x - radius) / WORLD.groundStep));
  const b = Math.min(state.terrain.length - 1, Math.ceil((x + radius) / WORLD.groundStep));
  for (let i = a; i <= b; i++) {
    const px = i * WORLD.groundStep;
    const d = Math.abs(px - x);
    if (d <= radius) {
      const delta = depth * Math.sqrt(Math.max(0, 1 - (d * d) / (radius * radius)));
      state.terrain[i] = Math.min(WORLD.h - 8, state.terrain[i] + delta);
    }
  }
}
export function applyBlast(state, x, y, weaponId, owner, scale = 1) {
  const weapon = WEAPONS[weaponId] || WEAPONS.classic;
  const radius = weapon.radius * scale;
  deformTerrain(state, x, weapon.radius * scale, weapon.crater * scale);
  for (const t of state.tanks) {
    const d = Math.hypot(t.x - x, t.y - y);
    if (d < radius + 38) {
      const damage = Math.max(0, weapon.damage * (1 - d / (radius + 38)) * scale);
      t.hp = Math.max(0, t.hp - damage);
      if (t.hp <= 0) {
        t.alive = false;
        state.winner = 1 - t.id;
      }
    }
  }
  if (weapon.behavior === 'napalm') state.fires.push({ x, radius: 88, turns: 2, owner });
  state.events.push({ type: 'explode', x, y, weapon: weaponId, owner });
  state.tanks.forEach(t => settleTank(state, t));
}
function impactProjectile(state, p) {
  const weapon = WEAPONS[p.weapon] || WEAPONS.classic;
  if (weapon.behavior === 'drill') p.y = Math.min(WORLD.h - 20, p.y + 44);
  p.dead = true;
  applyBlast(state, p.x, p.y, p.weapon, p.owner);
}
export function stepState(state, dt) {
  if (state.winner !== null) return;
  for (const p of state.projectiles) {
    if (p.dead) continue;
    const weapon = WEAPONS[p.weapon] || WEAPONS.classic;
    p.age += dt;
    p.vx += state.wind * WORLD.windAccel * dt;
    p.vy += WORLD.gravity * dt;
    p.x += p.vx * dt;
    p.y += p.vy * dt;
    if (weapon.behavior === 'cluster' && !p.split && p.age > 0.48 && p.vy > 0) {
      p.dead = true;
      p.split = true;
      for (let i = -2; i <= 2; i++) {
        state.projectiles.push({
          id: ++state.shotSerial,
          owner: p.owner,
          weapon: 'classic',
          x: p.x + i * 5,
          y: p.y,
          vx: p.vx + i * 42,
          vy: p.vy - 42 - Math.abs(i) * 7,
          age: 0,
          bounced: false,
          split: true,
          mini: true,
          dead: false,
        });
      }
    }
    if (p.x < 0 || p.x > WORLD.w || p.y > WORLD.h) {
      p.dead = true;
      continue;
    }
    if (p.y >= terrainY(state, p.x)) {
      if (weapon.behavior === 'ricochet' && !p.bounced && Math.abs(p.vy) > 50) {
        p.bounced = true;
        p.y = terrainY(state, p.x) - 5;
        p.vy = -Math.abs(p.vy) * 0.58;
        p.vx *= 0.82;
      } else impactProjectile(state, p);
    }
  }
  state.projectiles = state.projectiles.filter(p => !p.dead);
  if (state.phase === 'projectile' && state.projectiles.length === 0) {
    state.phase = 'resolving';
    state.resolveAt = nowMs() + 720;
  }
  if (state.phase === 'resolving' && nowMs() >= state.resolveAt) advanceTurn(state);
}
export function advanceTurn(state) {
  for (const f of state.fires) {
    for (const t of state.tanks) {
      if (Math.abs(t.x - f.x) < f.radius) {
        t.hp = Math.max(0, t.hp - 7);
        state.events.push({ type: 'burn', player: t.id, amount: 7 });
        if (t.hp <= 0) {
          t.alive = false;
          state.winner = 1 - t.id;
        }
      }
    }
    f.turns--;
  }
  state.fires = state.fires.filter(f => f.turns > 0);
  if (state.winner !== null) return;
  state.turn = 1 - state.turn;
  state.turnNumber++;
  state.tanks[state.turn].fuel = WORLD.turnFuel;
  state.wind = Math.round(randRange(state, -6, 6));
  state.phase = 'aim';
  state.turnEndsAt = nowMs() + WORLD.turnSeconds * 1000;
  state.events.push({ type: 'turn', player: state.turn });
}
export function validateAction(state, player, action) {
  if (!action || state.winner !== null) return false;
  if (action.type === 'move') return state.turn === player && state.phase === 'aim' && [-1, 1].includes(action.dir) && Number.isFinite(action.dt) && action.dt > 0 && action.dt <= 0.13;
  if (action.type === 'aim') return state.turn === player && state.phase === 'aim' && Number.isFinite(action.angle) && action.angle >= 5 && action.angle <= 175 && Number.isFinite(action.power) && action.power >= 20 && action.power <= 100;
  if (action.type === 'fire') return state.turn === player && state.phase === 'aim';
  return false;
}
export function applyAction(state, player, action) {
  if (!validateAction(state, player, action)) return false;
  if (action.type === 'move') moveTank(state, player, action.dir, action.dt);
  else if (action.type === 'aim') {
    state.tanks[player].angle = action.angle;
    state.tanks[player].power = action.power;
  } else if (action.type === 'fire') fire(state, player);
  return true;
}
export function aiChoose(state, difficulty = 'normal') {
  const ai = state.tanks[1];
  const target = state.tanks[0];
  let best = { score: -Infinity, angle: 135, power: 62 };
  const step = difficulty === 'hard' ? 2 : difficulty === 'normal' ? 4 : 7;
  const noise = difficulty === 'hard' ? 2 : difficulty === 'normal' ? 5 : 10;
  for (let angle = 92; angle <= 172; angle += step) {
    for (let power = 26; power <= 100; power += step * 2) {
      const fake = { ...ai, angle, power };
      const hit = predictedImpact(state, fake);
      const dist = Math.abs(hit.x - target.x);
      const score = -dist - Math.abs(hit.y - target.y) * 0.15;
      if (score > best.score) best = { score, angle, power };
    }
  }
  best.angle = Math.max(92, Math.min(175, best.angle + randRange(state, -noise, noise)));
  best.power = Math.max(22, Math.min(100, best.power + randRange(state, -noise, noise)));
  return best;
}
