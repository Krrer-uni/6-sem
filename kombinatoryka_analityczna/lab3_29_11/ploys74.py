import matplotlib.pyplot as plt
import numpy as np

from matplotlib import cm
from matplotlib.ticker import LinearLocator

# Make data.
dom_X = np.linspace(0,1,100)
dom_Y = np.linspace(0,4*np.pi,100)
dom_X, dom_Y = np.meshgrid(dom_X, dom_Y)

x = np.real(dom_X * np.exp(1j * dom_Y))
y = np.imag(dom_X * np.exp(1j * dom_Y))
z = np.absolute(np.log(dom_X) + 1j * dom_Y)

plot = plt.figure(figsize=(10, 8))
ax = plot.add_subplot(111, projection='3d')
surf = ax.plot_surface(x, y, z, cmap='viridis')

ax.set_xlabel('Re(e^(it))')
ax.set_ylabel('Im(e^(it))')
ax.set_zlabel('Re(sqrt(r) * exp(it/2))')
ax.set_title('Point 1')
plot.colorbar(surf, shrink=0.5, aspect=5)
plt.show()