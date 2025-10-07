import matplotlib.pyplot as plt

nodes = [200, 500, 2000, 5000, 10000]

S1Sparse = [5.14, 5.72, 6.7, 7.3, 7.96]
S2Sparse = [1.00, 1.00, 1.05, 1.11, 1.20]
S3Sparse = [1.18, 1.57, 3.02, 5.67, 10.52]

R1Sparse = [20.60, 24.52, 35.04, 43.92, 56.56]
R2Sparse = [36.18, 36.32, 37.08, 38.52, 40.83]
R3Sparse = [3.11, 4.07, 7.09, 14.85, 26.63]

S1Dense = [5.92, 6.48, 7.57, 8.38, 8.76]
S2Dense = [1.03, 1.01, 1.09, 1.32, 1.56]
S3Dense = [1.76, 2.65, 7.88, 19.59, 36.99]

R1Dense = [25.04, 34.12, 51.68, 78.16, 110.48]
R2Dense = [36.39, 36.97, 39.87, 46.11, 57.71]
R3Dense = [4.01, 7.50, 20.26, 49.39, 88.87]

#S1, S2, S3 Sparse
plt.subplot(2, 2, 1)
plt.plot(nodes, S1Sparse, label='S1', marker='o')
plt.plot(nodes, S2Sparse, label='S2', marker='s')
plt.plot(nodes, S3Sparse, label='S3', marker='^')
plt.title('S1, S2, S3 Sparse - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Nodes and Cells accessed')
plt.legend()
plt.grid(True)


#R1, R2, R3 Sparse
plt.subplot(2, 2, 2)
plt.plot(nodes, R1Sparse, label='R1', marker='o')
plt.plot(nodes, R2Sparse, label='R2', marker='s')
plt.plot(nodes, R3Sparse, label='R3', marker='^')
plt.title('R1, R2, R3 Sparse - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Nodes and Cells accessed')
plt.legend()
plt.grid(True)

#S1, S2, S3 Dense
plt.subplot(2, 2, 3)
plt.plot(nodes, S1Dense, label='S1', marker='o')
plt.plot(nodes, S2Dense, label='S2', marker='s')
plt.plot(nodes, S3Dense, label='S3', marker='^')
plt.title('S1, S2, S3 Dense - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Nodes and Cells accessed')
plt.legend()
plt.grid(True)

#R1, R2, R3 Dense
plt.subplot(2, 2, 4)
plt.plot(nodes, R1Dense, label='R1', marker='o')
plt.plot(nodes, R2Dense, label='R2', marker='s')
plt.plot(nodes, R3Dense, label='R3', marker='^')
plt.title('R1, R2, R3 Dense - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Nodes and Cells accessed')
plt.legend()
plt.grid(True)

plt.tight_layout()
plt.show()


plt.subplot(2, 2, 1)
plt.plot(nodes, S2Sparse, label='S2', marker='s', color='orange')
plt.title('S2 Sparse - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Nodes and Cells accessed')
plt.legend()
plt.grid(True)

plt.subplot(2, 2, 2)
plt.plot(nodes, R2Sparse, label='R2', marker='s', color='orange')
plt.title('R2 Sparse - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Nodes and Cells accessed')
plt.legend()
plt.grid(True)

plt.subplot(2, 2, 3)
plt.plot(nodes, S2Dense, label='S2', marker='s', color='orange')
plt.title('S2 Dense - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Nodes and Cells accessed')
plt.legend()
plt.grid(True)

plt.subplot(2, 2, 4)
plt.plot(nodes, R2Dense, label='R2', marker='s', color='orange')
plt.title('R2 Dense - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Nodes and Cells accessed')
plt.legend()
plt.grid(True)

plt.tight_layout()
plt.show()




