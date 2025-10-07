import matplotlib.pyplot as plt

nodes = [200, 500, 2000, 5000, 10000]

S1Sparse = [1241.06, 1006.99, 1717.96, 1776.95, 2482.01]
S2Sparse = [423.01, 337.02, 627.04, 614.03, 742.03]
S3Sparse = [467.97, 520.00, 1391.02, 1395.98, 3079.00]

R1Sparse = [2366.89, 2260.96, 3478.92, 4945.89, 7444.06]
R2Sparse = [8760.01, 7495.03, 9520.10, 11156.09, 14739.06]
R3Sparse = [1268.12, 1275.99, 2595.95, 4606.05, 8127.94]

S1Dense = [1307.00, 1293.95, 1483.88, 3796.99, 1563.94]
S2Dense = [402.04, 346.01, 527.00, 1454.99, 624.95]
S3Dense = [685.95, 776.05, 1353.19, 7657.06, 4041.10]

R1Dense = [2246.01, 2825.97, 4919.01, 15192.03, 12049.97]
R2Dense = [7745.01, 7452.01, 9371.96, 25312.94, 16483.02]
R3Dense = [1522.95, 2104.01, 4039.05, 16928.95, 14140.00]

#S1, S2, S3 Sparse
plt.subplot(2, 2, 1)
plt.plot(nodes, S1Sparse, label='S1', marker='o')
plt.plot(nodes, S2Sparse, label='S2', marker='s')
plt.plot(nodes, S3Sparse, label='S3', marker='^')
plt.title('S1, S2, S3 Sparse - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Time (ns)')
plt.legend()
plt.grid(True)

#R1, R2, R3 Sparse
plt.subplot(2, 2, 2)
plt.plot(nodes, R1Sparse, label='R1', marker='o')
plt.plot(nodes, R2Sparse, label='R2', marker='s')
plt.plot(nodes, R3Sparse, label='R3', marker='^')
plt.title('R1, R2, R3 Sparse - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Time (ns)')
plt.legend()
plt.grid(True)

#S1, S2, S3 Dense
plt.subplot(2, 2, 3)
plt.plot(nodes, S1Dense, label='S1', marker='o')
plt.plot(nodes, S2Dense, label='S2', marker='s')
plt.plot(nodes, S3Dense, label='S3', marker='^')
plt.title('S1, S2, S3 Dense - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Time (ns)')
plt.legend()
plt.grid(True)

#R1, R2, R3 Dense
plt.subplot(2, 2, 4)
plt.plot(nodes, R1Dense, label='R1', marker='o')
plt.plot(nodes, R2Dense, label='R2', marker='s')
plt.plot(nodes, R3Dense, label='R3', marker='^')
plt.title('R1, R2, R3 Dense - Nodes')
plt.xlabel('Nodes')
plt.ylabel('Time (ns)')
plt.legend()
plt.grid(True)

plt.tight_layout()
plt.show()


# plt.subplot(2, 2, 1)
# plt.plot(nodes, S2Sparse, label='S2', marker='s')
# plt.title('S2 Sparse - Nodes')
# plt.xlabel('Nodes')
# plt.ylabel('Time (ns)')
# plt.legend()
# plt.grid(True)

# plt.subplot(2, 2, 2)
# plt.plot(nodes, R2Sparse, label='R2', marker='s')
# plt.title('R2 Sparse - Nodes')
# plt.xlabel('Nodes')
# plt.ylabel('Time (ns)')
# plt.legend()
# plt.grid(True)

# plt.subplot(2, 2, 3)
# plt.plot(nodes, S2Dense, label='S2', marker='s')
# plt.title('S2 Dense - Nodes')
# plt.xlabel('Nodes')
# plt.ylabel('Time (ns)')
# plt.legend()
# plt.grid(True)

# plt.subplot(2, 2, 4)
# plt.plot(nodes, R2Dense, label='R2', marker='s')
# plt.title('R2 Dense - Nodes')
# plt.xlabel('Nodes')
# plt.ylabel('Time (ns)')
# plt.legend()
# plt.grid(True)

# plt.tight_layout()
# plt.show()